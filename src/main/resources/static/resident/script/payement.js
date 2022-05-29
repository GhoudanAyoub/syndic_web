$(document).ready(function() {
    $.ajax({
        url : '/api/sessions/resident',
        type : 'GET',
        async : false,
        success : function(data,
                           textStatus, jqXHR) {
            $("#residentId").val(data);
            $.ajax({
                url: '/api/residents/' + $("#residentId").val(),
                type: 'GET',
                async: false,
                success: function (data,
                                   textStatus, jqXHR) {
                    $("#headername").text(data.nom + " " + data.prenom);
                    if (data.photo != null) {
                        $("#headerimg").attr("src", data.photo);
                    }
                },
                error: function (jqXHR, textStatus,
                                 errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });
           //
            $.ajax({
                url : '/api/revenus/dates/' + $("#residentId").val(),
                type : 'GET',
                async : false,
                success : function(data,
                                   textStatus, jqXHR) {
                    remplirAnnee(data);
                },
                error : function(jqXHR, textStatus,
                                 errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });
            $.ajax({
                url : '/api/revenus/resident/' + $("#residentId").val(),
                type : 'GET',
                async : false,
                success : function(data,
                                   textStatus, jqXHR) {
                    remplir(data);
                },
                error : function(jqXHR, textStatus,
                                 errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });

        },
        error : function(jqXHR, textStatus,
                         errorThrown) {
            console.log(textStatus, errorThrown);
            swal("Echec!", "Erreur session!", "warning");
        }
    });

   /* $("#search").click(function () {
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
*/
    function remplirAnnee(data) {
        var ligne="";
        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                ligne += '<option value="' + data[i] + '">' + data[i] + '</option>';
                console.log(data[i]);
            }
        }

        $("#dateRech").html(ligne);
    }

    function remplir(data) {
        var ligne = "";
        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                    ligne += '<tr><td class="text-center">' + data[i].appartement.numero + '</td><td class="text-center">'+ data[i].description +  '</td><td class="text-center">'+ data[i].montant +  '</td><td class="text-center">'  + moment(data[i].date).format("YYYY-MM-DD") + '</td></tr>';
            }
        }else {
            ligne = '<td colspan="6" align="center"><p class="fs-2">Pas de payements !<p></td></tr>';
        }
        $("#table").html(ligne);

    }
});

