// Small inline spinner used wherever the app is waiting on an async
// request (document list loading, chat "thinking", uploads).
export default function Loader({ size = 18, label }) {
  const dimension = `${size}px`;

  return (
    <span
      style={{
        display: 'inline-flex',
        alignItems: 'center',
        gap: 8,
        color: 'var(--color-text-muted)',
        fontSize: 13,
      }}
    >
      <span
        style={{
          width: dimension,
          height: dimension,
          border: '2px solid var(--color-border)',
          borderTopColor: 'var(--color-primary)',
          borderRadius: '50%',
          display: 'inline-block',
          animation: 'spin 0.7s linear infinite',
        }}
      />
      {label}
      <style>
        {`@keyframes spin { to { transform: rotate(360deg); } }`}
      </style>
    </span>
  );
}
