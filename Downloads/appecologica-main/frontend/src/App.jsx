import Container from 'react-bootstrap/Container';
import NavigationBar from './components/NavigationBar.jsx';
import AppRoutes from './routes/AppRoutes.jsx';

function App() {
  return (
    <>
      <NavigationBar />
      <main className="py-4">
        <Container>
          <AppRoutes />
        </Container>
      </main>
    </>
  );
}

export default App;
