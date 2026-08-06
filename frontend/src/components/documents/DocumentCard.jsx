import Button from '../common/Button';

// Renders a single DocumentResponse (id, originalFileName, fileType,
// uploadedAt) with a delete action. Purely presentational — all data
// fetching/mutation lives in DocumentList.
export default function DocumentCard({ document, onDelete, deleting }) {
  const uploadedAt = document.uploadedAt
    ? new Date(document.uploadedAt).toLocaleString()
    : '';

  return (
    <div
      style={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        gap: 10,
        padding: '10px 12px',
        borderRadius: 8,
        background: 'var(--color-surface-alt)',
        marginBottom: 8,
      }}
    >
      <div style={{ minWidth: 0 }}>
        <p
          title={document.originalFileName}
          style={{
            margin: 0,
            fontSize: 13,
            fontWeight: 600,
            whiteSpace: 'nowrap',
            overflow: 'hidden',
            textOverflow: 'ellipsis',
          }}
        >
          {document.originalFileName}
        </p>
        <span style={{ fontSize: 11, color: 'var(--color-text-muted)' }}>
          {document.fileType?.toUpperCase()} · {uploadedAt}
        </span>
      </div>

      <Button
        variant="danger"
        disabled={deleting}
        onClick={() => onDelete(document.id)}
      >
        {deleting ? '…' : 'Delete'}
      </Button>
    </div>
  );
}
