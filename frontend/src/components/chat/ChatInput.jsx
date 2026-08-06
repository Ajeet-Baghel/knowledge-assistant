import { useState } from 'react';
import Button from '../common/Button';

// Controlled textarea + send button. Enter submits, Shift+Enter inserts
// a newline. Kept dumb (no API calls) so ChatWindow owns all chat state.
export default function ChatInput({ onSend, disabled }) {
  const [value, setValue] = useState('');

  const submit = () => {
    const trimmed = value.trim();
    if (!trimmed || disabled) return;
    onSend(trimmed);
    setValue('');
  };

  const handleKeyDown = (event) => {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault();
      submit();
    }
  };

  return (
    <div
      style={{
        display: 'flex',
        gap: 8,
        padding: 12,
        borderTop: '1px solid var(--color-border)',
      }}
    >
      <textarea
        value={value}
        onChange={(e) => setValue(e.target.value)}
        onKeyDown={handleKeyDown}
        placeholder="Ask a question about your documents…"
        rows={1}
        disabled={disabled}
        style={{
          flex: 1,
          resize: 'none',
          borderRadius: 8,
          border: '1px solid var(--color-border)',
          background: 'var(--color-surface-alt)',
          color: 'var(--color-text)',
          padding: '10px 12px',
          fontSize: 14,
          fontFamily: 'inherit',
          outline: 'none',
        }}
      />
      <Button onClick={submit} disabled={disabled || !value.trim()}>
        Send
      </Button>
    </div>
  );
}
