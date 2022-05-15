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



      $("#search").click(function () {
        $.ajax({
            url: '/api/revenusByImmeuble/' + $("#immeubleRech").val(),
            type: 'GET',
            async: false,
            success: function (data,
                               textStatus, jqXHR) {
                remplir(data);
            },
            error: function (jqXHR, textStatus,
                             errorThrown) {
                console.log(textStatus, errorThrown);
            }
        });
      });
    
    
});


       