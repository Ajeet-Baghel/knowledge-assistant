import { useCallback, useEffect, useState } from 'react';
import UploadBox from '../components/upload/UploadBox';
import DocumentList from '../components/documents/DocumentList';
import ChatWindow from '../components/chat/ChatWindow';
import documentService from '../services/documentService';

// Main page: left panel manages documents (upload + list), right panel
// is the chat. Owns the document list as the single source of truth so
// an upload or delete immediately refreshes what the chat is grounded on.
export default function Home() {
  const [documents, setDocuments] = useState([]);
  const [loading, setLoading] = useState(true);

  const loadDocuments = useCallback(async () => {
    setLoading(true);
    try {
      const data = await documentService.getAll();
      setDocuments(data);
    } catch (err) {
      console.error('Failed to load documents', err);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    loadDocuments();
  }, [loadDocuments]);

  return (
    <main className="app-main">
      <section className="panel">
        <div className="panel-header">Documents</div>
        <div className="panel-body scrollbar-thin">
          <UploadBox onUploaded={loadDocuments} />
          <div style={{ height: 16 }} />
          <DocumentList
            documents={documents}
            loading={loading}
            onChanged={loadDocuments}
          />
        </div>
      </section>

      <section className="panel">
        <div className="panel-header">Chat</div>
        <ChatWindow />
      </section>
    </main>
  );
}
