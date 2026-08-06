import Navbar from './components/common/Navbar';
import Home from './pages/Home';

// Root component: fixed header (Navbar) + the single page (Home) inside
// the app-shell flex layout defined in index.css.
export default function App() {
  return (
    <div className="app-shell">
      <Navbar />
      <Home />
    </div>
  );
}
