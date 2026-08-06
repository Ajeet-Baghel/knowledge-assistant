import { useState } from 'react';
import UploadButton from './UploadButton';
import Loader from '../common/Loader';
import documentService from '../../services/documentService';

// Drag-and-drop dropzone for uploading a PDF. Calls documentService.upload
// (POST /documents/upload) and notifies the parent via onUploaded so the
// document list can refresh. Handles drag state, progress, and errors
// locally so Home.jsx stays a thin orchestrator.
export default function UploadBox({ onUploaded }) {
  const [isDragging, setIsDragging] = useState(false);
  const [uploading, setUploading] = useState(false);
  const [progress, setProgress] = useState(0);
  const [error, setError] = useState(null);

  const handleUpload = async (file) => {
    setError(null);
    setUploading(true);
    setProgress(0);
    try {
      const document = await documentService.upload(file, (evt) => {
        if (evt.total) {
          setProgress(Math.round((evt.loaded * 100) / evt.total));
        }
      });
      onUploaded?.(document);
    } catch (err) {
      setError(err.message);
    } finally {
      setUploading(false);
      setProgress(0);
    }
  };

  const handleDrop = (event) => {
    event.preventDefault();
    setIsDragging(false);
    const file = event.dataTransfer.files?.[0];
    if (file) {
      handleUpload(file);
    }
  };

  return (
    <div
      onDragOver={(e) => {
        e.preventDefault();
        setIsDragging(true);
      }}
      onDragLeave={() => setIsDragging(false)}
      onDrop={handleDrop}
      style={{
        border: `2px dashed ${isDragging ? 'var(--color-primary)' : 'var(--color-border)'}`,
        borderRadius: 'var(--radius)',
        padding: 20,
        textAlign: 'center',
        background: isDragging ? 'var(--color-surface-alt)' : 'transparent',
        transition: 'all 0.15s ease',
      }}
    >
      <p style={{ margin: '0 0 10px', fontSize: 13, color: 'var(--color-text-muted)' }}>
        Drag & drop a PDF here, or
      </p>

      <UploadButton
        accept="application/pdf"
        disabled={uploading}
        onFileSelect={handleUpload}
      />

      {uploading && (
        <div style={{ marginTop: 12 }}>
          <Loader label={`Uploading… ${progress}%`} />
        </div>
      )}

      {error && (
        <p style={{ marginTop: 10, fontSize: 12, color: 'var(--color-danger)' }}>
          {error}
        </p>
      )}
    </div>
  );
}
