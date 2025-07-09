import React, { useState, useEffect, useCallback } from 'react';
import Navbar from '../components/Navbar';
import api_admin from '../services/apiAdmin';
import '../components/global.css';
import { useAuth } from '../hooks/useAuth';


interface ApiFile {
  name: string;
  isRestricted: boolean;
}

const FileIcon: React.FC<{ name: string }> = ({ name }) => {
  const extension = name.split('.').pop()?.toUpperCase() || '';
  let iconClass = 'bi bi-file-earmark-fill text-secondary'; 
  
  if (extension === 'PDF') iconClass = 'bi bi-file-earmark-pdf-fill text-danger';
  if (extension === 'DOCX') iconClass = 'bi bi-file-earmark-word-fill text-primary';
  if (extension === 'XLSX') iconClass = 'bi bi-file-earmark-excel-fill text-success';
  if (extension.match(/PNG|JPG|JPEG|GIF/)) iconClass = 'bi bi-file-earmark-image-fill text-info';
  
  return <i className={`${iconClass} fs-4`}></i>;
};

const DirectoryPage: React.FC = () => {
  const { user } = useAuth();
  const [files, setFiles] = useState<ApiFile[]>([]);
  const [fileToUpload, setFileToUpload] = useState<File | null>(null);
  const [isUploading, setIsUploading] = useState(false);
  const [isRestrictedUpload, setIsRestrictedUpload] = useState(false);
   const [isLoading, setIsLoading] = useState(true);

  const fetchFiles = useCallback(async () => {
    setIsLoading(true);
    try {
      const promises = [
        api_admin.get<string[]>('/admin/files/list')
      ];

      if (user?.roles.includes("ROLE_ADMIN")) {
        promises.push(api_admin.get<string[]>('/admin/files/restricted/list'));
      }
      
      const responses = await Promise.all(promises);
      const publicFiles = responses[0].data.map(name => ({ name, isRestricted: false }));
      const restrictedFiles = responses.length > 1 
        ? responses[1].data.map(name => ({ name, isRestricted: true })) 
        : [];
      
      setFiles([...publicFiles, ...restrictedFiles]);

    } catch (error) {
      console.error("Erro ao buscar a lista de arquivos:", error);
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchFiles();
  }, [fetchFiles]);

  const handleFileSelect = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      setFileToUpload(e.target.files[0]);
    }
  };

  const handleUpload = async () => {
    if (!fileToUpload) return;

    setIsUploading(true);
    const formData = new FormData();
    formData.append('file', fileToUpload);

    const uploadUrl = isRestrictedUpload
      ? '/admin/files/restricted/upload'
      : '/admin/files/upload';
    
    try {
      // Axios lida com o 'baseURL' e o 'Content-Type' para FormData automaticamente
      const response = await api_admin.post(uploadUrl, formData);
      
      alert(response.data);
      await fetchFiles();

    } catch (error) {
      console.error("Falha no upload:", error);
      alert("Ocorreu uma falha ao enviar o arquivo.");
    } finally {
      setIsUploading(false);
      setFileToUpload(null);
      setIsRestrictedUpload(false);
    }
  };

  const handleDownload = async (fileName: string, isRestricted: boolean) => {
    try {
      const downloadUrl = `/admin/files${isRestricted ? '/restricted' : ''}/download/${fileName}`;
      
      const response = await api_admin.get(downloadUrl, {
        responseType: 'blob', 
      });

      const url = window.URL.createObjectURL(new Blob([response.data]));

      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', fileName); 
      
      document.body.appendChild(link);
      link.click();
      link.remove();
      window.URL.revokeObjectURL(url);

    } catch (error) {
      console.error("Erro no download:", error);
      alert("Não foi possível baixar o arquivo. Verifique suas permissões.");
    }
  };

  return (
    <>
      <Navbar />
      <main className="container mt-4 mb-5">
        <header className="mb-4">
          <h1 className="fw-bolder">Diretório de Arquivos</h1>
          <p className="text-secondary fs-5">Consulte e gerencie os arquivos compartilhados do departamento.</p>
        </header>

        {/* 1. CRIE UMA ÚNICA ROW PARA ENVOLVER AMBAS AS COLUNAS */}
        <div className="row g-4">

          {/* 2. APLIQUE A CONDIÇÃO APENAS NA COLUNA DE UPLOAD */}
          {user?.roles.includes("ROLE_ADMIN") && (
            <div className="col-lg-4">
              <div className="card shadow-sm">
                <div className="card-body">
                  <h5 className="card-title fw-semibold mb-3">
                    <i className="bi bi-cloud-arrow-up-fill me-2"></i>Realizar Upload
                  </h5>
                  <input className="form-control" type="file" onChange={handleFileSelect} disabled={isUploading} />
                  <div className="form-check mt-3">
                    <input className="form-check-input" type="checkbox" id="restrictCheckbox" checked={isRestrictedUpload} onChange={(e) => setIsRestrictedUpload(e.target.checked)} disabled={isUploading}/>
                    <label className="form-check-label" htmlFor="restrictCheckbox">Arquivo Restrito (acesso limitado)</label>
                  </div>
                  <div className="d-grid mt-3">
                    <button className="btn btn-primary fw-semibold" onClick={handleUpload} disabled={!fileToUpload || isUploading}>
                      {isUploading ? <><span className="spinner-border spinner-border-sm me-2"></span>Enviando...</> : 'Enviar Arquivo'}
                    </button>
                  </div>
                </div>
              </div>
            </div>
          )}

          {/* 3. AJUSTE A LARGURA DA COLUNA DE ARQUIVOS DINAMICAMENTE */}
          <div className={user?.roles.includes("ROLE_ADMIN") ? "col-lg-8" : "col-lg-12"}>
            <div className="card shadow-sm">
              <div className="card-body">
                <h5 className="card-title fw-semibold mb-3"><i className="bi bi-folder-fill me-2"></i>Arquivos Armazenados</h5>
                <div className="table-responsive">
                  <table className="table table-hover align-middle">
                      <thead className="table-light">
                      <tr>
                        <th scope="col" style={{ width: '5%' }}></th>
                        <th scope="col">Nome do Arquivo</th>
                        <th scope="col">Ações</th>
                      </tr>
                    </thead>
                    <tbody>
                      {isLoading ? (
                        <tr><td colSpan={3} className="text-center p-4">Carregando arquivos...</td></tr>
                      ) : (
                        files.map(file => (
                          <tr key={file.name}>
                            <td><FileIcon name={file.name} /></td>
                            <td>
                              {file.isRestricted && <i className="bi bi-lock-fill text-warning me-2" title="Acesso Restrito"></i>}
                              {file.name}
                            </td>
                            <td>
                              <button 
                                className="btn btn-sm btn-outline-secondary" 
                                onClick={() => handleDownload(file.name, file.isRestricted)}
                              >
                                <i className="bi bi-download"></i>
                              </button>
                            </td>
                          </tr>
                        ))
                      )}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
          
        </div> 
      </main>
    </>
  );
}
export default DirectoryPage;