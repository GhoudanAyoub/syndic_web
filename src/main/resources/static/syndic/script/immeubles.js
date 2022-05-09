$(document).ready(function() {
    $("#uploadimg").on("click", function(){
        $("#photo").trigger("click");
    });
    $("#photo").on("change", function(){
        readIMG(this);
    });
    $("#deleteimg").on("click", function() {
        $("#img").prop("src", "");
        $("#photo").val("");
        $(this).prop('hidden', true);
    });
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
                ligne += '<tr><td class="text-center"><img style="width:150px;height:100px;" src="' + data[i].photo + '"></td><td class="text-center">' + data[i].numero + '</td><td class="text-center">' + data[i].nom + '</td><td class="text-center">' + data[i].adresse + '</td><td class="text-center">' + data[i].ville + '</td><td class="text-center">' + data[i].etages + '</td><td class="text-center"><div class="dropdown"><a class="btn btn-link font-24 p-0 line-height-1 no-arrow dropdown-toggle" href="#" role="button" data-toggle="dropdown"><i class="dw dw-more"></i></a><div class="dropdown-menu dropdown-menu-right dropdown-menu-icon-list"><a class="dropdown-item btn-update" data-immeuble=\'' + JSON.stringify(data[i]) + '\' href="javascript:void(0)"><i class="dw dw-edit2"></i> Modifier</a><a class="dropdown-item btn-delete" data-id="' + data[i].id + '" href="javascript:void(0)"><i class="dw dw-delete-3"></i> Supprimer</a></div></td></tr>';
            }
        }
        $("#table").html(ligne);

        $(".btn-update").click(function() {
            var immeuble = $(this).data("immeuble");

            $("#numero").val(immeuble.numero);
            $("#nom").val(immeuble.nom);
            $("#etages").val(immeuble.etages);
            $("#adresse").val(immeuble.adresse);
            $("#ville").val(immeuble.ville);

            $("#ajouter").prop('value', 'Modifier');
            $("#divannuler").prop('hidden', false);

            $("#annuler").click(function() {
                $("#ajouter").prop('value', 'Ajouter');
                $("#numero").val("");
                $("#nom").val("");
                $("#etages").val("");
                $("#adresse").val("");
                $("#ville").val("");
                $("#photo").val("");
                $("#divannuler").prop('hidden', true);
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
                                syndic : {id : immeuble.syndic.id},
                                numero : numero,
                                nom : nom,
                                etages : etages,
                                adresse : adresse,
                                ville : ville,
                                photo : baseString
                            };

                            $.ajax({
                                url : '/api/immeubles/' + immeuble.id,
                                contentType : 'application/json',
                                dataType : 'text',
                                data : JSON.stringify(json),
                                type : 'PUT',
                                async : false,
                                success : function(data,
                                                   textStatus, jqXHR) {
                                    swal("Succès!", "Modification de l'immeuble avec succès!", "success");
                                },
                                error : function(jqXHR, textStatus,
                                                 errorThrown) {
                                    console.log(textStatus, errorThrown);
                                    swal("Echec!", "Echec lors de la modification de l'immeuble!", "warning");
                                }
                            });
                        };
                        reader.readAsDataURL(file);
                    }else {
                        var json = {
                            syndic : {id : immeuble.syndic.id},
                            numero : numero,
                            nom : nom,
                            etages : etages,
                            adresse : adresse,
                            ville : ville,
                            photo : immeuble.photo
                        };

                        $.ajax({
                            url : '/api/immeubles/' + immeuble.id,
                            contentType : 'application/json',
                            dataType : 'text',
                            data : JSON.stringify(json),
                            type : 'PUT',
                            async : false,
                            success : function(data,
                                               textStatus, jqXHR) {
                                swal("Succès!", "Modification de l'immeuble avec succès!", "success");
                            },
                            error : function(jqXHR, textStatus,
                                             errorThrown) {
                                console.log(textStatus, errorThrown);
                                swal("Echec!", "Echec lors de la modification de l'immeuble!", "warning");
                            }
                        });
                    }
                }
            });

        });
    }
});

function readIMG(input) {
    if(input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            $("#img").attr("src", e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
        $("#deleteimg").prop('hidden', false);
    }
}

