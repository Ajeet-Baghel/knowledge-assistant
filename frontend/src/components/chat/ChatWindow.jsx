import { useEffect, useRef, useState } from 'react';
import ChatMessage from './ChatMessage';
import TypingIndicator from './TypingIndicator';
import ChatInput from './ChatInput';
import ragService from '../../services/ragService';

// Owns the full chat conversation: message history, sending a question
// to POST /rag/ask via ragService, and auto-scrolling to the latest
// message. This is the main panel of Home.jsx.
export default function ChatWindow() {
  const [messages, setMessages] = useState([]);
  const [asking, setAsking] = useState(false);
  const bottomRef = useRef(null);

  useEffect(() => {
    bottomRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages, asking]);

  const handleSend = async (question) => {
    setMessages((prev) => [...prev, { role: 'user', content: question }]);
    setAsking(true);
    try {
      const answer = await ragService.ask(question);
      setMessages((prev) => [...prev, { role: 'assistant', content: answer }]);
    } catch (err) {
      setMessages((prev) => [
        ...prev,
        { role: 'assistant', content: `Error: ${err.message}` },
      ]);
    } finally {
      setAsking(false);
    }
  };

  return (
    <>
      <div className="panel-body scrollbar-thin">
        {messages.length === 0 && !asking && (
          <p style={{ fontSize: 13, color: 'var(--color-text-muted)' }}>
            Ask a question about your uploaded documents to get started.
          </p>
        )}

        {messages.map((message, index) => (
          <ChatMessage key={index} role={message.role} content={message.content} />
        ))}

        {asking && <TypingIndicator />}
        <div ref={bottomRef} />
      </div>

      <ChatInput onSend={handleSend} disabled={asking} />
    </>
  );
}
