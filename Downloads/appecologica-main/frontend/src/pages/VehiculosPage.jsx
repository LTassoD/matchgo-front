import { useEffect, useState } from 'react';
import { Alert, Card } from 'react-bootstrap';
import ResourceTable from '../components/ResourceTable.jsx';
import { fetchVehiculos } from '../services/vehiculoService.js';

const columns = [
  { key: 'patente', label: 'Patente' },
  { key: 'modelo', label: 'Modelo' },
  { key: 'capacidadKg', label: 'Capacidad (kg)' },
  { key: 'habilitado', label: 'Activo' }
];

function VehiculosPage() {
  const [vehiculos, setVehiculos] = useState([]);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    const load = async () => {
      try {
        const data = await fetchVehiculos();
        setVehiculos(
          data.map((vehiculo) => ({
            ...vehiculo,
            habilitado: vehiculo.habilitado ? 'Sí' : 'No'
          }))
        );
      } catch (error) {
        setErrorMessage('No fue posible cargar los vehículos');
      }
    };
    load();
  }, []);

  return (
    <section>
      <h2 className="page-title">Vehículos</h2>
      <p className="text-muted">Listado de camiones autorizados para las rutas.</p>
      {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
      <Card>
        <Card.Body>
          <ResourceTable columns={columns} data={vehiculos} />
        </Card.Body>
      </Card>
    </section>
  );
}

export default VehiculosPage;
