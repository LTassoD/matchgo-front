import { Link } from 'react-router-dom';

function NotFoundPage() {
  return (
    <section className="text-center py-5">
      <h1>404</h1>
      <p className="text-muted">La página que buscas no existe.</p>
      <Link to="/">Volver al inicio</Link>
    </section>
  );
}

export default NotFoundPage;
