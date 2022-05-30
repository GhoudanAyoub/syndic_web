$(document).ready(function () {
    var deleted = false;
    var uploaded = false;
    $("#uploadimg").on("click", function () {
        $("#photo").trigger("click");
    });
    $("#photo").on("change", function () {
        readIMG(this);
        deleted = false;
        uploaded = true;
    });
    $("#deleteimg").on("click", function () {
        $("#img").prop("src", "");
        $("#photo").val("");
        $(this).prop('hidden', true);
        deleted = true;
        uploaded = false;
    });
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
                    $("#password").val(data.mdp);
                    $("#nom").val(data.nom);
                    $("#prenom").val(data.prenom);
                    $("#email").val(data.email);
                    $("#tel").val(data.telephone);
                    $("#ville").val(data.ville);
                    if (data.photo != null) {
                        $("#headerimg").attr("src", data.photo);
                        $("#img").attr("src", data.photo);
                        $("#deleteimg").prop('hidden', false);
                        deleted = false;
                        uploaded = false;
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
        var nom = $("#nom").val();
        var prenom = $("#prenom").val();
        var email = $("#email").val();
        var tel = $("#tel").val();
        var ville = $("#ville").val();
        var password = $("#password").val();
        var photo = null;
        if (uploaded) {
            photo = $("#photo").val();
        }

        if (deleted) {
            $("#img").attr("src", "");
        }

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

        if (verif) {
            if (photo) {
                var file = document.querySelector('input[type=file]')['files'][0];
                var reader = new FileReader();
                var baseString;
                reader.onloadend = function () {
                    baseString = reader.result;
                    var json = {
                        nom: nom,
                        prenom: prenom,
                        email: email,
                        telephone: tel,
                        ville: ville,
                        mdp: password,
                        photo: baseString
                    };

                    $.ajax({
                        url: '/api/residents/' + $("#residentId").val(),
                        contentType: 'application/json',
                        data: JSON.stringify(json),
                        type: 'PUT',
                        async: false,
                        success: function (data,
                                           textStatus, jqXHR) {
                            $.ajax({
                                url: '/api/residents/' + $("#residentId").val(),
                                type: 'GET',
                                async: false,
                                success: function (data,
                                                   textStatus, jqXHR) {
                                    $("#headername").text(data.nom + " " + data.prenom);
                                    $("#password").val(data.mdp);
                                    $("#nom").val(data.nom);
                                    $("#prenom").val(data.prenom);
                                    $("#email").val(data.email);
                                    $("#tel").val(data.telephone);
                                    $("#ville").val(data.ville);
                                    if (data.photo != null) {
                                        $("#headerimg").attr("src", data.photo);
                                        $("#img").attr("src", data.photo);
                                        $("#deleteimg").prop('hidden', false);
                                        deleted = false;
                                        uploaded = false;
                                    }else {
                                        $("#headerimg").attr("src", "images/no-image.png");
                                    }
                                    swal("Succès!", "Modification du profil avec succès!", "success");
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
                            swal("Echec!", "Echec lors de la modification du profil!", "warning");
                        }
                    });
                };
                reader.readAsDataURL(file);
            } else {
                var p = null;
                if ($("#img").attr("src") != "" && $("#img").attr("src") != "images/no-image.png") {
                    p = $("#img").attr("src");
                }
                var json = {
                    nom: nom,
                    prenom: prenom,
                    email: email,
                    telephone: tel,
                    ville: ville,
                    mdp: password,
                    photo: p
                };

                $.ajax({
                    url: '/api/residents/' + $("#residentId").val(),
                    contentType: 'application/json',
                    data: JSON.stringify(json),
                    type: 'PUT',
                    async: false,
                    success: function (data,
                                       textStatus, jqXHR) {
                        $.ajax({
                            url: '/api/residents/' + $("#residentId").val(),
                            type: 'GET',
                            async: false,
                            success: function (data,
                                               textStatus, jqXHR) {
                                $("#headername").text(data.nom + " " + data.prenom);
                                $("#password").val(data.mdp);
                                $("#nom").val(data.nom);
                                $("#prenom").val(data.prenom);
                                $("#email").val(data.email);
                                $("#tel").val(data.telephone);
                                $("#ville").val(data.ville);
                                if (data.photo != null) {
                                    $("#headerimg").attr("src", data.photo);
                                    $("#img").attr("src", data.photo);
                                    $("#deleteimg").prop('hidden', false);
                                    deleted = false;
                                    uploaded = false;
                                }else {
                                    $("#headerimg").attr("src", "images/no-image.png");
                                }
                                swal("Succès!", "Modification du profil avec succès!", "success");
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
                        swal("Echec!", "Echec lors de la modification du profil!", "warning");
                    }
                });
            }
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

