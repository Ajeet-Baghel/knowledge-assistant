// App-level header. Keeps branding and (future) global actions
// separate from the page content in Home.jsx.
export default function Navbar() {
  return (
    <header
      style={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        padding: '14px 24px',
        borderBottom: '1px solid var(--color-border)',
        background: 'var(--color-surface)',
      }}
    >
      <div style={{ display: 'flex', alignItems: 'center', gap: 10 }}>
        <span style={{ fontSize: 20 }}>📚</span>
        <h1 style={{ fontSize: 16, margin: 0, fontWeight: 700 }}>
          Enterprise Knowledge Assistant
        </h1>
      </div>
      <span style={{ fontSize: 12, color: 'var(--color-text-muted)' }}>
        RAG over your documents
      </span>
    </header>
  );
}
