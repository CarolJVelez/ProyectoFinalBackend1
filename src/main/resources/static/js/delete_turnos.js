function deleteBy(id)
{
    if (confirm("¿Estás seguro de que deseas eliminar este turno?")) {
          const url = '/turnos/eliminar/'+ id;
          const settings = {
              method: 'DELETE'
          }
          fetch(url,settings)
              .then(response => {
                  if (response.ok) {
                      // Si la eliminación es exitosa, borrar la fila de la tabla
                      let row = document.getElementById('tr_' + id);
                      if (row) {
                          row.remove();
                      }
                      alert("Turno eliminado con exito")
                  } else {
                      return response.json().then(error => {
                          console.error('Error al eliminar el turno:', error);
                      });
                  }
              })
              .catch(error => console.error('Error en la solicitud de eliminación:', error));
    }
}