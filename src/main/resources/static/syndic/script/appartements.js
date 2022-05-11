$(document).ready(function() {
    var id;
    var deleted = false;
    var uploaded = false;



    $.ajax({
        url : '/api/sessions',
        type : 'GET',
        async : false,
        success : function(data,
                           textStatus, jqXHR) {
            id = data;
            $.ajax({
                url : '/api/appartements/' + id,
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

            $("#ajouter").click(function(e) {
                if($(this).attr("value") == "Ajouter") {
                    e.preventDefault();
                    var verif = true;
                    var numero = $("#numero").val();
                    var etage = $("#etage").val();
                    var surface = $("#surface").val();
                    var debut = $("#fin").val();
                    var fin = $("#fin").val();
                    var resident = $("#resident").val();
                    var immeuble = $("#immeuble").val();

                    if (numero == "") {
                        $("#numero").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#numero").css("border", "1px solid #d4d4d4");
                    }

                    if (etage == "") {
                        $("#etage").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#etage").css("border", "1px solid #d4d4d4");
                    }

                    if (surface == "") {
                        $("#surface").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#surface").css("border", "1px solid #d4d4d4");
                    }

                    if (debut == "") {
                        $("#debut").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#debut").css("border", "1px solid #d4d4d4");
                    }

                    if (fin == "") {
                        $("#fin").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#fin").css("border", "1px solid #d4d4d4");
                    }

                    if (verif) {

                            var json = {
                                resident : {id : id},
                                immeuble : {id : id},
                                numero : numero,
                                etage : etage,
                                surface : surface,
                                debut : debut,
                                fin : fin,


                            };

                            $.ajax({
                                url : '/api/immeubles',
                                contentType : 'application/json',
                                data : JSON.stringify(json),
                                type : 'POST',
                                async : false,
                                success : function(data,
                                                   textStatus, jqXHR) {
                                    remplir(data);
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
                ligne += '<tr><td class="text-center"><img style="width:150px;height:100px;" src="' + data[i].photo + '" onerror="this.src=\'images/no-image.png\'"></td><td class="text-center">' + data[i].numero + '</td><td class="text-center">' + data[i].nom + '</td><td class="text-center">' + data[i].adresse + '</td><td class="text-center">' + data[i].ville + '</td><td class="text-center">' + data[i].etages + '</td><td class="text-center"><div class="dropdown"><a class="btn btn-link font-24 p-0 line-height-1 no-arrow dropdown-toggle" href="#" role="button" data-toggle="dropdown"><i class="dw dw-more"></i></a><div class="dropdown-menu dropdown-menu-right dropdown-menu-icon-list"><a class="dropdown-item btn-update" data-immeuble=\'' + JSON.stringify(data[i]) + '\' href="javascript:void(0)"><i class="dw dw-edit2"></i> Modifier</a><a class="dropdown-item btn-delete" data-id="' + data[i].id + '" href="javascript:void(0)"><i class="dw dw-delete-3"></i> Supprimer</a></div></td></tr>';
            }
        }
        $("#table").html(ligne);

        $(".btn-delete").click(function() {
            swal({
                title: "Voulez-vous supprimer cet immeuble?",
                icon: "info",
                buttons: true,
                showcancelbutton: true,
            })
                .then((isConfirm) => {
                    if (isConfirm) {
                        var delid = $(this).data("id");
                        $.ajax({
                            url : '/api/immeubles/' + delid,
                            contentType : 'application/json',
                            type : 'DELETE',
                            async : false,
                            success : function(data,
                                               textStatus, jqXHR) {
                                remplir(data);
                                swal("Succès!", "Suppression de l'immeuble avec succès!", "success");
                            },
                            error : function(jqXHR, textStatus,
                                             errorThrown) {
                                console.log(textStatus, errorThrown);
                                swal("Echec!", "Echec lors de la suppression de l'immeuble!", "warning");
                            }
                        });
                    }
                });
        });

        $(".btn-update").click(function() {
            var immeuble = $(this).data("immeuble");

            $("#numero").val(immeuble.numero);
            $("#nom").val(immeuble.nom);
            $("#etages").val(immeuble.etages);
            $("#adresse").val(immeuble.adresse);
            $("#ville").val(immeuble.ville);
            console.log("immeuble.photo 1 : " + immeuble.photo);
            if(immeuble.photo != null) {
                $("#img").attr("src", immeuble.photo);
                $("#deleteimg").prop('hidden', false);
                deleted = false;
            }
            console.log("immeuble.photo 1.5 : " + immeuble.photo);

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
                $("#img").attr("src", "");
                $("#divannuler").prop('hidden', true);
                $("#deleteimg").prop('hidden', true);
                deleted = false;
                uploaded = false;
            });

            $("#ajouter").click(function(e) {
                if($(this).attr("value") == "Modifier") {
                    e.preventDefault();
                    console.log("immeuble.photo 2 : " + immeuble.photo);
                    var verif = true;
                    var numero = $("#numero").val();
                    var nom = $("#nom").val();
                    var etages = $("#etages").val();
                    var adresse = $("#adresse").val();
                    var ville = $("#ville").val();
                    if(uploaded) {
                        var photo = $("#photo").val();
                    }

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
                        console.log("immeuble.photo 3 : " + immeuble.photo);
                        if(photo) {
                            console.log("one");
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
                                    data : JSON.stringify(json),
                                    type : 'PUT',
                                    async : false,
                                    success : function(data,
                                                       textStatus, jqXHR) {
                                        remplir(data);
                                        $("#ajouter").prop('value', 'Ajouter');
                                        $("#numero").val("");
                                        $("#nom").val("");
                                        $("#etages").val("");
                                        $("#adresse").val("");
                                        $("#ville").val("");
                                        $("#photo").val("");
                                        $("#img").attr("src", "");
                                        $("#divannuler").prop('hidden', true);
                                        $("#deleteimg").prop('hidden', true);
                                        deleted = false;
                                        uploaded = false;
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
                            console.log("two : " + immeuble.photo);
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
                                data : JSON.stringify(json),
                                type : 'PUT',
                                async : false,
                                success : function(data,
                                                   textStatus, jqXHR) {
                                    remplir(data);
                                    $("#ajouter").prop('value', 'Ajouter');
                                    $("#numero").val("");
                                    $("#nom").val("");
                                    $("#etages").val("");
                                    $("#adresse").val("");
                                    $("#ville").val("");
                                    $("#photo").val("");
                                    $("#img").attr("src", "");
                                    $("#divannuler").prop('hidden', true);
                                    $("#deleteimg").prop('hidden', true);
                                    deleted = false;
                                    uploaded = false;
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

