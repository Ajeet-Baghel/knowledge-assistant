// Animated "…" bubble shown in place of ChatMessage while we wait for
// POST /rag/ask to resolve, giving the user feedback the LLM is working.
export default function TypingIndicator() {
  return (
    <div style={{ display: 'flex', justifyContent: 'flex-start', marginBottom: 10 }}>
      <div
        style={{
          display: 'flex',
          gap: 4,
          padding: '12px 16px',
          borderRadius: 12,
          borderBottomLeftRadius: 2,
          background: 'var(--color-surface-alt)',
        }}
      >
        {[0, 1, 2].map((i) => (
          <span
            key={i}
            style={{
              width: 6,
              height: 6,
              borderRadius: '50%',
              background: 'var(--color-text-muted)',
              animation: `bounce 1s ${i * 0.15}s infinite`,
            }}
          />
        ))}
      </div>
      <style>
        {`@keyframes bounce {
            0%, 60%, 100% { transform: translateY(0); opacity: 0.5; }
            30% { transform: translateY(-4px); opacity: 1; }
          }`}
      </style>
    </div>
  );
}
