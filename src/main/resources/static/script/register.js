$(document).ready(function() {
    $("#inscrire").click(function(e) {
        e.preventDefault();
        var verif = true;
        var nom = $("#nom").val();
        var prenom = $("#prenom").val();
        var email = $("#email").val();
        var tel = $("#tel").val();
        var ville = $("#ville").val();
        var photo = $("#photo").val();
        var password = $("#password").val();
        var passwordconf = $("#passwordconf").val();
        if (nom == "") {
            $("#nom").css("border", "1px solid red");
            verif = false;
        } else {
            $("#nom").css("border", "1px solid #d4d4d4");
        }

        if (prenom == "") {
            $("#prenom").css("border", "1px solid red");
            verif = false;
        } else {
            $("#prenom").css("border", "1px solid #d4d4d4");
        }

        if (email == "") {
            $("#email").css("border", "1px solid red");
            verif = false;
        } else {
            $("#email").css("border", "1px solid #d4d4d4");
        }

        if (tel == "") {
            $("#tel").css("border", "1px solid red");
            verif = false;
        } else {
            $("#tel").css("border", "1px solid #d4d4d4");
        }

        if (ville == "") {
            $("#ville").css("border", "1px solid red");
            verif = false;
        } else {
            $("#ville").css("border", "1px solid #d4d4d4");
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
                $("#password-error").attr('hidden', false);
            }else {
                $("#password-error").attr('hidden', true);
            }
        }


        if (verif) {
            if(photo) {
                var file = document.querySelector('input[type=file]')['files'][0];
                var reader = new FileReader();
                var baseString;
                reader.onloadend = function () {
                    baseString = reader.result;
                    console.log(baseString);
                    var json = {
                        nom : nom,
                        prenom : prenom,
                        email : email,
                        telephone : tel,
                        ville : ville,
                        photo : baseString,
                        mdp : password
                    };

                    $.ajax({
                        url : '/api/syndics',
                        contentType : 'application/json',
                        dataType : 'text',
                        data : JSON.stringify(json),
                        type : 'POST',
                        async : false,
                        success : function(data,
                                           textStatus, jqXHR) {
                            console.log('syndic done');
                            swal("Succès!", "Inscription avec succès!", "success").then(() => {
                                location.href = "index.html";
                            });
                        },
                        error : function(jqXHR, textStatus,
                                         errorThrown) {
                            console.log(textStatus, errorThrown);
                            swal("Echec!", "Echec lors de l'inscription!", "warning");
                        }
                    });
                };
                reader.readAsDataURL(file);
            }else {
                var json = {
                    nom : nom,
                    prenom : prenom,
                    email : email,
                    telephone : tel,
                    ville : ville,
                    photo : null,
                    mdp : password
                };

                $.ajax({
                    url : '/api/syndics',
                    contentType : 'application/json',
                    dataType : 'text',
                    data : JSON.stringify(json),
                    type : 'POST',
                    async : false,
                    success : function(data,
                                       textStatus, jqXHR) {
                        console.log('syndic done');
                        swal("Succès!", "Inscription avec succès!", "success").then(() => {
                            location.href = "index.html";
                        });
                    },
                    error : function(jqXHR, textStatus,
                                     errorThrown) {
                        console.log(textStatus, errorThrown);
                        swal("Echec!", "Echec lors de l'inscription!", "warning");
                    }
                });
            }

/*            $.ajax({
                url: "/syndics",
                data: { op: "insert", nom: nom, prenom: prenom, cin: cin, password: password },
                type: 'POST',
                success: function(data, textStatus, jqXHR) {
                    if (data != null) {
                        $("#nomI").val("");
                        $("#prenomI").val("");
                        $("#cinI").val("");
                        $("#passwordI").val("");
                        $("#insert-client").modal("hide");
                        swal("Succès!", "Création du client avec succès!", "success").then(() => {
                            remplir(data);
                        });
                    } else {
                        swal("Echec!", "Echec lors de la création du client!", "warning");
                    }
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(errorThrown);
                }
            });*/
        }
    });
});

