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
                    remplirDates(data);
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



    function remplirDates(data) {
        var ligne = "<option hidden></option>";
        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                ligne += '<option>' + data[i] + '</option>';
            }
        }
        $("#annee").html(ligne);

        $("#annee").on('change', function()
        {
            $.ajax({
                url : '/api/revenus/' + $("#residentId").val() + '/' + this.value,
                type : 'GET',
                async : false,
                success : function(data,
                                   textStatus, jqXHR) {
                    //console.log(data);
                    //console.log(Object.values(data)[0]['3']);
                    remplirPayement(data);
                },
                error : function(jqXHR, textStatus,
                                 errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });

        });
    }

    function remplirPayement(data) {
        var ligne = "";
        if (Object.keys(data).length > 0) {
            for (var i = 0; i < Object.keys(data).length; i++) {
                var s = 0;
                for(var j = '1'; j <= '12'; j++) {
                    s += Object.values(data)[i][j];
                }
                ligne += '<tr><td class="text-center">' + Object.keys(data)[i] + '</td><td class="text-center">' + Object.values(data)[i]['1'] + '</td><td class="text-center">' + Object.values(data)[i]['2'] + '</td><td class="text-center">' + Object.values(data)[i]['3'] + '</td><td class="text-center">' + Object.values(data)[i]['4'] + '</td><td class="text-center">' + Object.values(data)[i]['5'] + '</td><td class="text-center">' + Object.values(data)[i]['6'] + '</td><td class="text-center">' + Object.values(data)[i]['7'] + '</td><td class="text-center">' + Object.values(data)[i]['8'] + '</td><td class="text-center">' + Object.values(data)[i]['9'] + '</td><td class="text-center">' + Object.values(data)[i]['10'] + '</td><td class="text-center">' + Object.values(data)[i]['11'] + '</td><td class="text-center">' + Object.values(data)[i]['12'] + '</td><td class="text-center">' + s + '</td></tr>';
            }
        }else {
            ligne = '<td colspan="13" align="center"><p class="fs-2">Pas de revenus !<p></td></tr>';
        }
        $("#table-releve").html(ligne);
    }
});

