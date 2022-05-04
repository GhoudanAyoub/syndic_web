$(document).ready(function() {
    $.ajax({
        url : '/api/sessions',
        type : 'GET',
        async : false,
        success : function(data,
                           textStatus, jqXHR) {
            var id = data;
            $.ajax({
                url : '/api/immeubles/syndic/' + id,
                type : 'GET',
                async : false,
                success : function(data,
                                   textStatus, jqXHR) {
                    console.log(data);
                    remplir(data);
                },
                error : function(jqXHR, textStatus,
                                 errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });

            $("#ajouter").click(function(e) {
                e.preventDefault();
                var verif = true;
                var numero = $("#numero").val();
                var nom = $("#nom").val();
                var etages = $("#etages").val();
                var adresse = $("#adresse").val();
                var ville = $("#ville").val();
                var photo = $("#photo").val();

                if (numero == "") {
                    $("#numero").css("border", "1px solid red");
                    verif = false;
                } else {
                    $("#numero").css("border", "1px solid #d4d4d4");
                }

                if (nom == "") {
                    $("#nom").css("border", "1px solid red");
                    verif = false;
                } else {
                    $("#nom").css("border", "1px solid #d4d4d4");
                }

                if (etages == "") {
                    $("#etages").css("border", "1px solid red");
                    verif = false;
                } else {
                    $("#etages").css("border", "1px solid #d4d4d4");
                }

                if (adresse == "") {
                    $("#adresse").css("border", "1px solid red");
                    verif = false;
                } else {
                    $("#adresse").css("border", "1px solid #d4d4d4");
                }

                if (ville == "") {
                    $("#ville").css("border", "1px solid red");
                    verif = false;
                } else {
                    $("#ville").css("border", "1px solid #d4d4d4");
                }

                if (verif) {
                    if(photo) {
                        var file = document.querySelector('input[type=file]')['files'][0];
                        var reader = new FileReader();
                        var baseString;
                        reader.onloadend = function () {
                            baseString = reader.result;
                            var json = {
                                syndic : {id : id},
                                numero : numero,
                                nom : nom,
                                etages : etages,
                                adresse : adresse,
                                ville : ville,
                                photo : baseString
                            };

                            $.ajax({
                                url : '/api/immeubles',
                                contentType : 'application/json',
                                dataType : 'text',
                                data : JSON.stringify(json),
                                type : 'POST',
                                async : false,
                                success : function(data,
                                                   textStatus, jqXHR) {
                                    swal("Succès!", "Ajout de l'immeuble avec succès!", "success");
                                },
                                error : function(jqXHR, textStatus,
                                                 errorThrown) {
                                    console.log(textStatus, errorThrown);
                                    swal("Echec!", "Echec lors de l'ajout de l'immeuble!", "warning");
                                }
                            });
                        };
                        reader.readAsDataURL(file);
                    }else {
                        var json = {
                            syndic : {id : id},
                            numero : numero,
                            nom : nom,
                            etages : etages,
                            adresse : adresse,
                            ville : ville,
                            photo : null
                        };

                        $.ajax({
                            url : '/api/immeubles',
                            contentType : 'application/json',
                            dataType : 'text',
                            data : JSON.stringify(json),
                            type : 'POST',
                            async : false,
                            success : function(data,
                                               textStatus, jqXHR) {
                                swal("Succès!", "Ajout de l'immeuble avec succès!", "success");
                            },
                            error : function(jqXHR, textStatus,
                                             errorThrown) {
                                console.log(textStatus, errorThrown);
                                swal("Echec!", "Echec lors de l'ajout de l'immeuble!", "warning");
                            }
                        });
                    }
                }
            });
        },
        error : function(jqXHR, textStatus,
                         errorThrown) {
            console.log(textStatus, errorThrown);
            swal("Echec!", "Erreur session!", "warning");
        }
    });

    function remplir(data) {
        var ligne = "";
        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                ligne += '<tr><td align="center"><span class="user-icon"><img src="' + data[i].photo + '"></span></td><td align="center">' + data[i].numero + '</td><td align="center">' + data[i].nom + '</td><td align="center">' + data[i].adresse + '</td><td align="center">' + data[i].ville + '</td><td align="center">' + data[i].etages + '</td><td><div class="dropdown"><a class="btn btn-link font-24 p-0 line-height-1 no-arrow dropdown-toggle" href="#" role="button" data-toggle="dropdown"><i class="dw dw-more"></i></a><div class="dropdown-menu dropdown-menu-right dropdown-menu-icon-list"><a class="dropdown-item" href="#"><i class="dw dw-edit2"></i> Modifier</a><a class="dropdown-item" href="#"><i class="dw dw-delete-3"></i> Supprimer</a></div></td></tr>';
            }
        }
        $("#table").html(ligne);
    }
});

