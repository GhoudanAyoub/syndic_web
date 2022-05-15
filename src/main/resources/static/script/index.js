$(document).ready(function () {
    $("#connecter").click(function (e) {
        e.preventDefault();
        var verif = true;
        var email = $("#email").val();
        var password = $("#password").val();


        if (email == "") {
            $("#email").css("border", "1px solid red");
            verif = false;
        } else {
            $("#email").css("border", "1px solid #d4d4d4");
        }

        if (password == "") {
            $("#password").css("border", "1px solid red");
            verif = false;
        } else {
            $("#password").css("border", "1px solid #d4d4d4");
        }

        if (verif) {
            $.ajax({
                url: '/login',
                type: 'POST',
                data: {email: email, password: password},
                async: false,
                success: function (data,
                                   textStatus, jqXHR) {
                    $.ajax({
                        url: '/api/sessions/save?email=' + email,
                        type: 'POST',
                        async: false,
                        success: function (data,
                                           textStatus, jqXHR) {
                            console.log("session done : ", data);
                            location.href = "/syndic/home.html";
                        },
                        error: function (jqXHR, textStatus,
                                         errorThrown) {
                            console.log(textStatus, errorThrown);
                            swal("Echec!", "Erreur session!", "warning");
                        }
                    });
                },
                error: function (jqXHR, textStatus,
                                 errorThrown) {
                    console.log(textStatus, errorThrown);
                    swal("Echec!", "Adresse email ou mot de passe invalide!", "warning");
                }
            });
        }
    });
});

