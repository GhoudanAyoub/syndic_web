$(document).ready(function () {
    $.ajax({
        url: '/api/sessions/resident',
        type: 'GET',
        async: false,
        success: function (data,
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
        },
        error: function (jqXHR, textStatus,
                         errorThrown) {
            console.log(textStatus, errorThrown);
            swal("Echec!", "Erreur session!", "warning");
        }
    });

    $("#modifier").click(function (e) {
        e.preventDefault();
        var verif = true;
        var passwordold = $("#passwordold").val();
        var password = $("#password").val();
        var passwordconf = $("#passwordconf").val();


        if (passwordold == "") {
            $("#passwordold").css("border", "1px solid red");
            verif = false;
        } else {
            $("#passwordold").css("border", "1px solid #d4d4d4");
        }

        if (password == "") {
            $("#password").css("border", "1px solid red");
            verif = false;
        } else {
            $("#password").css("border", "1px solid #d4d4d4");
        }

        if (passwordconf == "") {
            $("#passwordconf").css("border", "1px solid red");
            verif = false;
        } else {
            $("#passwordconf").css("border", "1px solid #d4d4d4");
        }

        if(password != "" && passwordconf != "") {
            if(password != passwordconf) {
                verif = false;
                $("#password-error").prop('hidden', false);
            }else {
                $("#password-error").prop('hidden', true);
            }
        }

        if(verif) {
            $.ajax({
                url: '/api/residents/ancientpassword/' + $("#residentId").val() + '/' + passwordold,
                type: 'GET',
                async: false,
                success: function (data,
                                   textStatus, jqXHR) {
                    if(data == 1) {
                        var json = {
                            mdp: password
                        };
                        $.ajax({
                            url: '/api/residents/password/' + $("#residentId").val(),
                            contentType: 'application/json',
                            data: JSON.stringify(json),
                            type: 'PUT',
                            async: false,
                            success: function (data,
                                               textStatus, jqXHR) {
                                $("#passwordold").val("");
                                $("#password").val("");
                                $("#passwordconf").val("");
                                swal("Succès!", "Modification du mot de passe avec succès!", "success");
                            },
                            error: function (jqXHR, textStatus,
                                             errorThrown) {
                                console.log(textStatus, errorThrown);
                                swal("Echec!", "Echec lors de la modification du mot de passe!", "warning");
                            }
                        });
                    }else {
                        swal("Echec!", "L'ancien mot de passe et le nouveau mot de passe ne sont pas identiques!", "warning");
                    }
                },
                error: function (jqXHR, textStatus,
                                 errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });
        }
    });
});

function readIMG(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $("#img").attr("src", e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
        $("#deleteimg").prop('hidden', false);
    }
}

