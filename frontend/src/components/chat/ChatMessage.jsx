// Renders a single chat bubble. `role` is either "user" or "assistant"
// and drives alignment/coloring so the conversation reads like a
// familiar chat UI.
export default function ChatMessage({ role, content }) {
  const isUser = role === 'user';

  return (
    <div
      style={{
        display: 'flex',
        justifyContent: isUser ? 'flex-end' : 'flex-start',
        marginBottom: 10,
      }}
    >
      <div
        style={{
          maxWidth: '75%',
          padding: '10px 14px',
          borderRadius: 12,
          fontSize: 14,
          lineHeight: 1.5,
          whiteSpace: 'pre-wrap',
          background: isUser ? 'var(--color-primary)' : 'var(--color-surface-alt)',
          color: isUser ? '#fff' : 'var(--color-text)',
          borderBottomRightRadius: isUser ? 2 : 12,
          borderBottomLeftRadius: isUser ? 12 : 2,
        }}
      >
        {content}
      </div>
    </div>
  );
}
