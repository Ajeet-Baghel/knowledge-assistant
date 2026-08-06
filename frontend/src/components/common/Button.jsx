// Reusable button with a small set of visual variants so every
// call-to-action across the app (send, upload, delete) looks consistent.
const VARIANT_STYLES = {
  primary: {
    background: 'var(--color-primary)',
    color: '#fff',
  },
  ghost: {
    background: 'transparent',
    color: 'var(--color-text)',
    border: '1px solid var(--color-border)',
  },
  danger: {
    background: 'transparent',
    color: 'var(--color-danger)',
    border: '1px solid var(--color-danger)',
  },
};

export default function Button({
  children,
  onClick,
  variant = 'primary',
  disabled = false,
  type = 'button',
  fullWidth = false,
}) {
  const variantStyle = VARIANT_STYLES[variant] || VARIANT_STYLES.primary;

  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled}
      style={{
        ...variantStyle,
        padding: '9px 16px',
        borderRadius: 8,
        border: variantStyle.border || 'none',
        fontSize: 14,
        fontWeight: 600,
        cursor: disabled ? 'not-allowed' : 'pointer',
        opacity: disabled ? 0.5 : 1,
        width: fullWidth ? '100%' : 'auto',
        transition: 'opacity 0.15s ease, background 0.15s ease',
      }}
    >
      {children}
    </button>
  );
}
