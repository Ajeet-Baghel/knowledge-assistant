import { useRef } from 'react';
import Button from '../common/Button';

// Small standalone trigger: hides the native <input type="file"> behind
// a styled Button and forwards the chosen File to the parent via
// onFileSelect. Used by UploadBox for its "browse" fallback, and can be
// reused anywhere else a simple upload trigger is needed.
export default function UploadButton({ onFileSelect, accept, disabled }) {
  const inputRef = useRef(null);

  const handleChange = (event) => {
    const file = event.target.files?.[0];
    if (file) {
      onFileSelect(file);
    }
    event.target.value = '';
  };

  return (
    <>
      <input
        ref={inputRef}
        type="file"
        accept={accept}
        style={{ display: 'none' }}
        onChange={handleChange}
      />
      <Button
        variant="ghost"
        disabled={disabled}
        onClick={() => inputRef.current?.click()}
      >
        Browse file
      </Button>
    </>
  );
}
