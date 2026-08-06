import { useState } from 'react';
import DocumentCard from './DocumentCard';
import Loader from '../common/Loader';
import documentService from '../../services/documentService';

// Renders the list of uploaded documents. Receives data/loading state as
// props (owned by Home.jsx, the single source of truth) but owns the
// delete mutation itself since that only affects this list's rows.
export default function DocumentList({ documents, loading, onChanged }) {
  const [deletingId, setDeletingId] = useState(null);

  const handleDelete = async (id) => {
    setDeletingId(id);
    try {
      await documentService.remove(id);
      onChanged?.();
    } finally {
      setDeletingId(null);
    }
  };

  if (loading) {
    return <Loader label="Loading documents…" />;
  }

  if (!documents || documents.length === 0) {
    return (
      <p style={{ fontSize: 13, color: 'var(--color-text-muted)' }}>
        No documents uploaded yet.
      </p>
    );
  }

  return (
    <div>
      {documents.map((doc) => (
        <DocumentCard
          key={doc.id}
          document={doc}
          deleting={deletingId === doc.id}
          onDelete={handleDelete}
        />
      ))}
    </div>
  );
}
