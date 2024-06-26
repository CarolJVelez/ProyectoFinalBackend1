window.addEventListener('load', function () {


    //Buscamos y obtenemos el formulario donde estan
    //los datos que el usuario pudo haber modificado de la pelicula
    const formulario = document.querySelector('#update_paciente_form');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault();
        let pacienteId = document.querySelector('#paciente_id').value;

        //creamos un JSON que tendrá los datos de la película
        //a diferencia de una pelicula nueva en este caso enviamos el id
        //para poder identificarla y modificarla para no cargarla como nueva
        const formData = {
            id: document.querySelector('#paciente_id').value,
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            cedula: document.querySelector('#cedula').value,
            fechaIngreso: document.querySelector('#fechaIngreso').value,
            domicilio: {
                calle: document.querySelector('#calle').value,
                numero: document.querySelector('#numero').value,
                localidad: document.querySelector('#localidad').value,
                provincia: document.querySelector('#provincia').value
            },
            email: document.querySelector('#email').value

        };

        //invocamos utilizando la función fetch la API peliculas con el método PUT que modificará
        //la película que enviaremos en formato JSON
        if (confirm("¿Estás seguro de que deseas actualizar este paciente?")) {
        const url = '/paciente';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
        fetch(url,settings)
            .then(response => response.text())
            .then(data => {
                console.log("Paciente actualizado:", data);
                mostrarMensaje(data, 'success');
                // Recarga la página después de un breve retraso
                setTimeout(() => {
                    window.location.reload();
                }, 2000);
            })
            .catch(error => {
                console.error("Error actualizando el paciente:", error);
                mostrarMensaje('Error actualizando el paciente: ' + error.message, 'danger');
            });
        }
    })
})
function mostrarMensaje(mensaje, tipo) {
    const responseDiv = document.querySelector('#response');
    responseDiv.innerHTML = `
        <div class="alert alert-${tipo} alert-dismissible fade show" role="alert">
            ${mensaje}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>`;
    responseDiv.style.display = 'block';
    setTimeout(() => {
        responseDiv.style.display = 'none';
    }, 5000);
}
//Es la funcion que se invoca cuando se hace click sobre el id de una pelicula del listado
//se encarga de llenar el formulario con los datos de la pelicula
//que se desea modificar
function findBy(id) {
    const url = '/paciente/buscar/' +id;
    const settings = {
        method: 'GET'
    }
    fetch(url,settings)
        .then(response => response.json())
        .then(data => {
            let paciente = data;
            document.querySelector('#paciente_id').value = paciente.id || '';
            document.querySelector('#nombre').value = paciente.nombre || '';
            document.querySelector('#apellido').value = paciente.apellido || '';
            document.querySelector('#cedula').value = paciente.cedula || '';
            document.querySelector('#fechaIngreso').value = paciente.fechaIngreso || '';
            document.querySelector('#calle').value = paciente?.domicilio?.calle || '';
            document.querySelector('#numero').value = paciente?.domicilio?.numero || '';
            document.querySelector('#localidad').value = paciente?.domicilio?.localidad || '';
            document.querySelector('#provincia').value = paciente?.domicilio?.provincia || '';
            document.querySelector('#email').value = paciente.email || '';
            // Mostrar el formulario de actualización
            document.querySelector('#div_paciente_updating').style.display = "block";
        }).catch(error => {
        alert("Error: " + error);
    })
}