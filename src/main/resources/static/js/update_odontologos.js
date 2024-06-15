window.addEventListener('load', function () {


    //Buscamos y obtenemos el formulario donde estan
    //los datos que el usuario pudo haber modificado de la pelicula
    const formulario = document.querySelector('#update_odontologo_form');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault();
        let odontologoId = document.querySelector('#odontologo_id').value;

        //creamos un JSON que tendrá los datos de la película
        //a diferencia de una pelicula nueva en este caso enviamos el id
        //para poder identificarla y modificarla para no cargarla como nueva
        const formData = {
            id: document.querySelector('#odontologo_id').value,
            matricula: document.querySelector('#matricula').value,
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,

        };

        //invocamos utilizando la función fetch la API peliculas con el método PUT que modificará
        //la película que enviaremos en formato JSON
        if (confirm("¿Estás seguro de que deseas actualizar este odontologo?")) {
        const url = '/odontologos';
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
                  console.log("Odontologo actualizado:", data);

                  mostrarMensaje(data, 'success');
                  // Recarga la página después de un breve retraso
                  setTimeout(() => {
                      window.location.reload();
                  }, 2000);
              })
              .catch(error => {
                  console.error("Error actualizando el Odontologo:", error);
                  mostrarMensaje('Error actualizando el Odontologo: ' + error.message, 'danger');
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
          const url = '/odontologos'+"/"+id;
          const settings = {
              method: 'GET'
          }
          fetch(url,settings)
          .then(response => response.json())
          .then(data => {
              let odontologo = data;
              document.querySelector('#odontologo_id').value = odontologo.id;
              document.querySelector('#matricula').value = odontologo.matricula;
              document.querySelector('#nombre').value = odontologo.nombre;
              document.querySelector('#apellido').value = odontologo.apellido;
              //el formulario por default esta oculto y al editar se habilita
              document.querySelector('#div_odontologo_updating').style.display = "block";
          }).catch(error => {
              alert("Error: " + error);
          })
      }