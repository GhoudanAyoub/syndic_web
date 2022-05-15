$(document).ready(function() {
    $.ajax({
        url : '/api/sessions',
        type : 'GET',
        async : false,
        success : function(data,
                           textStatus, jqXHR) {
            console.log("my id is : ", data);
        },
        error : function(jqXHR, textStatus,
                         errorThrown) {
            console.log(textStatus, errorThrown);
            swal("Echec!", "Erreur session!", "warning");
        }
    });


    

    $.ajax({
        url : '/api/revenus/annee',
        type : 'GET',
        async : false,
        success : function(data,
            textStatus, jqXHR) {
            
            remplir(data);

          
        },
        error : function(jqXHR, textStatus,
                         errorThrown) {
            console.log(textStatus, errorThrown);
            swal("Echec!", "Erreur session!", "warning");
        }
    });



    

    function remplir(data) {
       
        const ctx = document.getElementById('myChart').getContext('2d');

        const myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: Object.keys(data),
                datasets: [
                    {
                        label: 'Number of GitHub Stars',
                        data: Object.values(data),
                    },
                ],
            },
        });
    }
    
    
});


       