$(document).ready(function() {
    var deleted = false;
    var uploaded = false;
    $("#uploadimg").on("click", function(){
        $("#photo").trigger("click");
    });
    $("#photo").on("change", function(){
        readIMG(this);
        deleted = false;
        uploaded = true;
    });
    $("#deleteimg").on("click", function() {
        $("#img").prop("src", "");
        $("#photo").val("");
        $(this).prop('hidden', true);
        deleted = true;
        uploaded = false;
    });
    $.ajax({
        url : '/api/sessions',
        type : 'GET',
        async : false,
        success : function(data,
                           textStatus, jqXHR) {
            $("#syndicId").val(data);
            $.ajax({
                url : '/api/appartements/syndic/immeuble/' + $("#syndicId").val(),
                type : 'GET',
                async : false,
                success : function(data,
                                   textStatus, jqXHR) {
                    remplirSelect(data);
                },
                error : function(jqXHR, textStatus,
                                 errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });
            $.ajax({
                url : '/api/residents/syndic/' + $("#syndicId").val(),
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
                    var nom = $("#nom").val();
                    var prenom = $("#prenom").val();
                    var email = $("#email").val();
                    var tel = $("#tel").val();
                    var ville = $("#ville").val();
                    var password = $("#password").val();
                    var photo = $("#photo").val();
                    var appartements = $("#appartement").val();

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

                    if (verif) {
                        if(photo) {
                            var file = document.querySelector('input[type=file]')['files'][0];
                            var reader = new FileReader();
                            var baseString;
                            reader.onloadend = function () {
                                baseString = reader.result;
                                var json = {
                                    nom : nom,
                                    prenom : prenom,
                                    email : email,
                                    telephone : tel,
                                    ville : ville,
                                    mdp : password,
                                    photo : baseString
                                };

                                $.ajax({
                                    url : '/api/residents/' + $("#syndicId").val(),
                                    contentType : 'application/json',
                                    data : JSON.stringify(json),
                                    type : 'POST',
                                    async : false,
                                    success : function(data,
                                                       textStatus, jqXHR) {
                                        var residentId = data.id;
                                        $.ajax({
                                            url : '/api/appartements/resident/' + residentId,
                                            data : {appartementId : appartements},
                                            type : 'PUT',
                                            async : false,
                                            success : function(textStatus, jqXHR) {
                                                $.ajax({
                                                    url : '/api/residents/syndic/' + $("#syndicId").val(),
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
                                                $("#residentId").val("");
                                                $("#nom").val("");
                                                $("#prenom").val("");
                                                $("#email").val("");
                                                $("#tel").val("");
                                                $("#ville").val("");
                                                $("#password").val("");
                                                $("#photo").val("");
                                                $("#img").attr("src", "");
                                                $("#appartement").val("");
                                                $("#divannuler").prop('hidden', true);
                                                $("#deleteimg").prop('hidden', true);
                                                deleted = false;
                                                uploaded = false;
                                                swal("Succès!", "Ajout du résident avec succès!", "success");
                                            },
                                            error : function(jqXHR, textStatus,
                                                             errorThrown) {
                                                console.log(textStatus, errorThrown);
                                                swal("Echec!", "Echec lors de l'ajout du résident!", "warning");
                                            }
                                        });
                                    },
                                    error : function(jqXHR, textStatus,
                                                     errorThrown) {
                                        console.log(textStatus, errorThrown);
                                        swal("Echec!", "Echec lors de l'ajout du résident!", "warning");
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
                                mdp : password,
                                photo : null
                            };

                            $.ajax({
                                url : '/api/residents/' + $("#syndicId").val(),
                                contentType : 'application/json',
                                data : JSON.stringify(json),
                                type : 'POST',
                                async : false,
                                success : function(data,
                                                   textStatus, jqXHR) {
                                    var residentId = data.id;
                                    $.ajax({
                                        url : '/api/appartements/resident/' + residentId,
                                        data : {appartementId : appartements},
                                        type : 'PUT',
                                        async : false,
                                        success : function(textStatus, jqXHR) {
                                            $.ajax({
                                                url : '/api/residents/syndic/' + $("#syndicId").val(),
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
                                            $("#residentId").val("");
                                            $("#nom").val("");
                                            $("#prenom").val("");
                                            $("#email").val("");
                                            $("#tel").val("");
                                            $("#ville").val("");
                                            $("#password").val("");
                                            $("#photo").val("");
                                            $("#img").attr("src", "");
                                            $("#appartement").val("");
                                            $("#divannuler").prop('hidden', true);
                                            $("#deleteimg").prop('hidden', true);
                                            deleted = false;
                                            uploaded = false;
                                            swal("Succès!", "Ajout du résident avec succès!", "success");
                                        },
                                        error : function(jqXHR, textStatus,
                                                         errorThrown) {
                                            console.log(textStatus, errorThrown);
                                            swal("Echec!", "Echec lors de l'ajout du résident!", "warning");
                                        }
                                    });
                                },
                                error : function(jqXHR, textStatus,
                                                 errorThrown) {
                                    console.log(textStatus, errorThrown);
                                    swal("Echec!", "Echec lors de l'ajout du résident!", "warning");
                                }
                            });
                        }
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

    function remplirSelect(data) {
        var ligne = "";
        if (data.length > 0) {
            var immeubleId = data[0].immeuble.id;
            ligne += '<optgroup label="'+ data[0].immeuble.nom +'">';
            ligne += '<option value="'+ data[0].id +'">'+ data[0].immeuble.nom + ' : appartement '  + data[0].numero +'</option>';
            for (var i = 1; i < data.length; i++) {
                if(data[i].immeuble.id == immeubleId) {
                    ligne += '<option value="'+ data[i].id +'">'+ data[i].immeuble.nom + ' : appartement ' + data[i].numero +'</option>';
                }else {
                    ligne += '</optgroup>';
                    ligne += '<optgroup label="'+ data[i].immeuble.nom +'">';
                    ligne += '<option value="'+ data[i].id +'">'+ data[i].immeuble.nom + ' : appartement ' + data[i].numero +'</option>';
                    immeubleId = data[i].immeuble.id;
                }
            }
        }
        $("#appartement").html(ligne);
    }

    function remplir(data) {
        var ligne = "";
        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                ligne += '<tr><td class="text-center"><img style="width:150px;height:100px;" src="' + data[i].photo + '" onerror="this.src=\'images/no-image.png\'"></td><td class="text-center">' + data[i].nom + '</td><td class="text-center">' + data[i].prenom + '</td><td class="text-center">' + data[i].email + '</td><td class="text-center">' + data[i].telephone + '</td><td class="text-center">' + data[i].ville + '</td><td class="text-center">' + data[i].mdp + '</td><td class="text-center"><div class="dropdown"><a class="btn btn-link font-24 p-0 line-height-1 no-arrow dropdown-toggle" href="#" role="button" data-toggle="dropdown"><i class="dw dw-more"></i></a><div class="dropdown-menu dropdown-menu-right dropdown-menu-icon-list"><a class="dropdown-item btn-update" data-resident=\'' + JSON.stringify(data[i]) + '\' href="javascript:void(0)"><i class="dw dw-edit2"></i> Modifier</a><a class="dropdown-item btn-delete" data-id="' + data[i].id + '" href="javascript:void(0)"><i class="dw dw-delete-3"></i> Supprimer</a><a class="dropdown-item btn-appartement" data-id="' + data[i].id + '" href="javascript:void(0)"><i class="dw dw-house1"></i> Appartements</a></div></td></tr>';
            }
        }
        $("#table").html(ligne);

        $(".btn-delete").click(function() {
            swal({
                title: "Voulez-vous supprimer ce résident?",
                icon: "info",
                buttons: true,
                showcancelbutton: true,
            })
                .then((isConfirm) => {
                    if (isConfirm) {
                        var delid = $(this).data("id");
                        $.ajax({
                            url : '/api/residents/' + $("#syndicId").val() + '/' + delid,
                            contentType : 'application/json',
                            type : 'DELETE',
                            async : false,
                            success : function(data,
                                               textStatus, jqXHR) {
                                remplir(data);
                                swal("Succès!", "Suppression du résident avec succès!", "success");
                            },
                            error : function(jqXHR, textStatus,
                                             errorThrown) {
                                console.log(textStatus, errorThrown);
                                swal("Echec!", "Echec lors de la suppression du résident!", "warning");
                            }
                        });
                    }
                });
        });

        $(".btn-update").click(function() {
            var resident = $(this).data("resident");

            $("#residentId").val(resident.id);
            $("#nom").val(resident.nom);
            $("#prenom").val(resident.prenom);
            $("#email").val(resident.email);
            $("#tel").val(resident.telephone);
            $("#ville").val(resident.ville);
            $("#password").val(resident.mdp);
            if(resident.photo != null) {
                $("#img").attr("src", resident.photo);
                $("#deleteimg").prop('hidden', false);
                deleted = false;
                uploaded = false;
            }

            $.ajax({
                url : '/api/appartements/syndic/resident/immeuble/' + $("#syndicId").val() + '/' + $("#residentId").val(),
                type : 'GET',
                async : false,
                success : function(data,
                                   textStatus, jqXHR) {
                   remplirSelect(data);
                },
                error : function(jqXHR, textStatus,
                                 errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });

            $.ajax({
                url : '/api/appartements/resident/' + $("#residentId").val(),
                type : 'GET',
                async : false,
                success : function(data,
                                   textStatus, jqXHR) {
                    var ids = [];
                    for(var i = 0; i < data.length; i++) {
                        ids.push(data[i].id);
                    }
                    $("#appartement").val(ids);
                },
                error : function(jqXHR, textStatus,
                                 errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });

            $("#ajouter").prop('value', 'Modifier');
            $("#divannuler").prop('hidden', false);

            $("#annuler").click(function() {
                $.ajax({
                    url : '/api/appartements/syndic/immeuble/' + $("#syndicId").val(),
                    type : 'GET',
                    async : false,
                    success : function(data,
                                       textStatus, jqXHR) {
                        remplirSelect(data);
                    },
                    error : function(jqXHR, textStatus,
                                     errorThrown) {
                        console.log(textStatus, errorThrown);
                    }
                });
                $("#ajouter").prop('value', 'Ajouter');
                $("#residentId").val("");
                $("#nom").val("");
                $("#prenom").val("");
                $("#email").val("");
                $("#tel").val("");
                $("#ville").val("");
                $("#password").val("");
                $("#photo").val("");
                $("#img").attr("src", "");
                $("#appartement").val("");
                $("#divannuler").prop('hidden', true);
                $("#deleteimg").prop('hidden', true);
                deleted = false;
                uploaded = false;
            });

            $("#ajouter").click(function(e) {
                if($(this).attr("value") == "Modifier") {
                    e.preventDefault();
                    var verif = true;
                    var residentId = $("#residentId").val();
                    var nom = $("#nom").val();
                    var prenom = $("#prenom").val();
                    var email = $("#email").val();
                    var tel = $("#tel").val();
                    var ville = $("#ville").val();
                    var password = $("#password").val();
                    var photo = null;
                    if(uploaded) {
                        photo = $("#photo").val();
                    }

                    if(deleted) {
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

                    if (password == "") {
                        $("#password").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#password").css("border", "1px solid #d4d4d4");
                    }

                    if (verif) {
                        if(photo) {
                            var file = document.querySelector('input[type=file]')['files'][0];
                            var reader = new FileReader();
                            var baseString;
                            reader.onloadend = function () {
                                baseString = reader.result;
                                var json = {
                                    nom : nom,
                                    prenom : prenom,
                                    email : email,
                                    telephone : tel,
                                    ville : ville,
                                    mdp : password,
                                    photo : baseString
                                };

                                $.ajax({
                                    url : '/api/residents/' + $("#syndicId").val() + '/' + residentId,
                                    contentType : 'application/json',
                                    data : JSON.stringify(json),
                                    type : 'PUT',
                                    async : false,
                                    success : function(data,
                                                       textStatus, jqXHR) {
                                        remplir(data);
                                        $.ajax({
                                            url : '/api/appartements/syndic/immeuble/' + $("#syndicId").val(),
                                            type : 'GET',
                                            async : false,
                                            success : function(data,
                                                               textStatus, jqXHR) {
                                                remplirSelect(data);
                                            },
                                            error : function(jqXHR, textStatus,
                                                             errorThrown) {
                                                console.log(textStatus, errorThrown);
                                            }
                                        });
                                        $("#ajouter").prop('value', 'Ajouter');
                                        $("#residentId").val("");
                                        $("#nom").val("");
                                        $("#prenom").val("");
                                        $("#email").val("");
                                        $("#tel").val("");
                                        $("#ville").val("");
                                        $("#password").val("");
                                        $("#photo").val("");
                                        $("#img").attr("src", "");
                                        $("#appartement").val("");
                                        $("#divannuler").prop('hidden', true);
                                        $("#deleteimg").prop('hidden', true);
                                        deleted = false;
                                        uploaded = false;
                                        swal("Succès!", "Modification du résident avec succès!", "success");
                                    },
                                    error : function(jqXHR, textStatus,
                                                     errorThrown) {
                                        console.log(textStatus, errorThrown);
                                        swal("Echec!", "Echec lors de la modification du résident!", "warning");
                                    }
                                });
                            };
                            reader.readAsDataURL(file);
                        }else {
                            var p = null;
                            if($("#img").attr("src") != "" && $("#img").attr("src") != "images/no-image.png") {
                                p = $("#img").attr("src");
                            }
                            var json = {
                                nom : nom,
                                prenom : prenom,
                                email : email,
                                telephone : tel,
                                ville : ville,
                                mdp : password,
                                photo : p
                            };

                            $.ajax({
                                url : '/api/residents/' + $("#syndicId").val() + residentId,
                                contentType : 'application/json',
                                data : JSON.stringify(json),
                                type : 'PUT',
                                async : false,
                                success : function(data,
                                                   textStatus, jqXHR) {
                                    remplir(data);
                                    $.ajax({
                                        url : '/api/appartements/syndic/immeuble/' + $("#syndicId").val(),
                                        type : 'GET',
                                        async : false,
                                        success : function(data,
                                                           textStatus, jqXHR) {
                                            remplirSelect(data);
                                        },
                                        error : function(jqXHR, textStatus,
                                                         errorThrown) {
                                            console.log(textStatus, errorThrown);
                                        }
                                    });
                                    $("#ajouter").prop('value', 'Ajouter');
                                    $("#residentId").val("");
                                    $("#nom").val("");
                                    $("#prenom").val("");
                                    $("#email").val("");
                                    $("#tel").val("");
                                    $("#ville").val("");
                                    $("#password").val("");
                                    $("#photo").val("");
                                    $("#img").attr("src", "");
                                    $("#appartement").val("");
                                    $("#divannuler").prop('hidden', true);
                                    $("#deleteimg").prop('hidden', true);
                                    deleted = false;
                                    uploaded = false;
                                    swal("Succès!", "Modification du résident avec succès!", "success");
                                },
                                error : function(jqXHR, textStatus,
                                                 errorThrown) {
                                    console.log(textStatus, errorThrown);
                                    swal("Echec!", "Echec lors de la modification du résident!", "warning");
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

