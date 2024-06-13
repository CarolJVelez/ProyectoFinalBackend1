function deleteBy(id) {
    // Confirmación antes de eliminar
    if (confirm("¿Estás seguro de que deseas eliminar este paciente?")) {
        // Si el usuario confirma la eliminación
        const url = '/paciente/eliminar/' + id;
        const settings = {
            method: 'DELETE'
        };

        fetch(url, settings)
            .then(response => {
                if (response.ok) {
                    // Eliminación exitosa
                    let row = document.getElementById('tr_' + id);
                    if (row) {
                        row.remove();
                    }
                    alert("Paciente eliminado con exito")

                } else {
                    // Error al intentar eliminar
                    return response.json().then(error => {
                        console.error('Error al eliminar el paciente:', error);
                    });
                }
            })
            .catch(error => console.error('Error en la solicitud de eliminación:', error));
    }
}
