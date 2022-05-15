$(document).ready(function () {
    $.ajax({
        url: '/api/sessions',
        type: 'GET',
        async: false,
        success: function (data,
                           textStatus, jqXHR) {
            $("#syndicId").val(data);
            $.ajax({
                url: '/api/syndics/' + $("#syndicId").val(),
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
            $.ajax({
                url: '/api/immeubles/syndic/' + $("#syndicId").val(),
                type: 'GET',
                async: false,
                success: function (data,
                                   textStatus, jqXHR) {
                    remplirSelect(data);
                },
                error: function (jqXHR, textStatus,
                                 errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });

            $.ajax({
                url: '/api/appartements/syndic/' + $("#syndicId").val(),
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

            $("#ajouter").click(function (e) {
                if ($(this).attr("value") == "Ajouter") {
                    e.preventDefault();
                    var verif = true;
                    var immeubleId = $("#immeuble").val();
                    var numero = $("#numero").val();
                    var etage = $("#etage").val();
                    var surface = $("#surface").val();

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

                    if (verif) {
                        var json = {
                            immeuble: {id: immeubleId},
                            numero: numero,
                            etage: etage,
                            surface: surface,
                            resident: null
                        };

                        $.ajax({
                            url: '/api/appartements',
                            contentType: 'application/json',
                            data: JSON.stringify(json),
                            type: 'POST',
                            async: false,
                            success: function (data,
                                               textStatus, jqXHR) {
                                $.ajax({
                                    url: '/api/appartements/syndic/' + $("#syndicId").val(),
                                    type: 'GET',
                                    async: false,
                                    success: function (data,
                                                       textStatus, jqXHR) {
                                        remplir(data);
                                        $("#appartementId").val("");
                                        $("#numero").val("");
                                        $("#etage").val("");
                                        $("#surface").val("");
                                        swal("Succès!", "Ajout de l'appartement avec succès!", "success");
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
                                swal("Echec!", "Echec lors de l'ajout de l'appartement!", "warning");
                            }
                        });
                    }
                }
            });
        },
        error: function (jqXHR, textStatus,
                         errorThrown) {
            console.log(textStatus, errorThrown);
            swal("Echec!", "Erreur session!", "warning");
        }
    });

    $("#search").click(function () {
        $.ajax({
            url: '/api/appartements/immeuble/' + $("#immeubleRech").val(),
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

    $("#annulerRech").click(function() {
        $.ajax({
            url : '/api/appartements/syndic/' + $("#syndicId").val(),
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
    });

    function remplirSelect(data) {
        var ligne = "";
        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                ligne += '<option value="' + data[i].id + '">' + data[i].nom + '</option>';
            }
        }
        $("#immeuble").html(ligne);
        $("#immeubleRech").html(ligne);
    }

    function remplir(data) {
        var ligne = "";
        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                ligne += '<tr><td class="text-center">' + data[i].immeuble.nom + '</td><td class="text-center">' + data[i].numero + '</td><td class="text-center">' + data[i].etage + '</td><td class="text-center">' + data[i].surface + '</td><td class="text-center"><div class="dropdown"><a class="btn btn-link font-24 p-0 line-height-1 no-arrow dropdown-toggle" href="#" role="button" data-toggle="dropdown"><i class="dw dw-more"></i></a><div class="dropdown-menu dropdown-menu-right dropdown-menu-icon-list"><a class="dropdown-item btn-update" data-appartement=\'' + JSON.stringify(data[i]) + '\' href="javascript:void(0)"><i class="dw dw-edit2"></i> Modifier</a><a class="dropdown-item btn-delete" data-id="' + data[i].id + '" href="javascript:void(0)"><i class="dw dw-delete-3"></i> Supprimer</a></div></td></tr>';
            }
        }else {
            ligne = '<td colspan="5" align="center"><p class="fs-2">Pas d\'appartements !<p></td></tr>';
        }
        $("#table").html(ligne);

        $(".btn-delete").click(function () {
            swal({
                title: "Voulez-vous supprimer cet appartement?",
                icon: "info",
                buttons: true,
                showcancelbutton: true,
            })
                .then((isConfirm) => {
                    if (isConfirm) {
                        var delid = $(this).data("id");
                        $.ajax({
                            url: '/api/appartements/' + delid,
                            contentType: 'application/json',
                            type: 'DELETE',
                            async: false,
                            success: function (data,
                                               textStatus, jqXHR) {
                                $.ajax({
                                    url: '/api/appartements/syndic/' + $("#syndicId").val(),
                                    type: 'GET',
                                    async: false,
                                    success: function (data,
                                                       textStatus, jqXHR) {
                                        remplir(data);
                                        swal("Succès!", "Suppression de l'appartement avec succès!", "success");
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
                                swal("Echec!", "Echec lors de la suppression de l'appartement!", "warning");
                            }
                        });
                    }
                });
        });

        $(".btn-update").click(function () {
            var appartement = $(this).data("appartement");

            $("#appartementId").val(appartement.id);
            $("#immeuble").val(appartement.immeuble.id);
            $("#immeuble").change();
            $("#numero").val(appartement.numero);
            $("#etage").val(appartement.etage);
            $("#surface").val(appartement.surface);

            $("#ajouter").prop('value', 'Modifier');
            $("#divannuler").prop('hidden', false);

            $("#annuler").click(function () {
                $("#ajouter").prop('value', 'Ajouter');
                $("#appartementId").val("");
                $("#numero").val("");
                $("#etage").val("");
                $("#surface").val("");
            });

            $("#ajouter").click(function (e) {
                if ($(this).attr("value") == "Modifier") {
                    e.preventDefault();
                    var verif = true;
                    var appartementId = $("#appartementId").val();
                    var immeubleId = $("#immeuble").val();
                    var numero = $("#numero").val();
                    var etage = $("#etage").val();
                    var surface = $("#surface").val();

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

                    if (verif) {
                        var json = {
                            immeuble: {id: immeubleId},
                            numero: numero,
                            etage: etage,
                            surface: surface,
                            resident: null
                        };

                        $.ajax({
                            url: '/api/appartements/' + appartementId,
                            contentType: 'application/json',
                            data: JSON.stringify(json),
                            type: 'PUT',
                            async: false,
                            success: function (data,
                                               textStatus, jqXHR) {
                                $.ajax({
                                    url: '/api/appartements/syndic/' + $("#syndicId").val(),
                                    type: 'GET',
                                    async: false,
                                    success: function (data,
                                                       textStatus, jqXHR) {
                                        remplir(data);
                                        $("#ajouter").prop('value', 'Ajouter');
                                        $("#appartementId").val("");
                                        $("#numero").val("");
                                        $("#etage").val("");
                                        $("#surface").val("");
                                        swal("Succès!", "Modification de l'appartement avec succès!", "success");
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
                                swal("Echec!", "Echec lors de la modification de l'appartement!", "warning");
                            }
                        });
                    }
                }
            });

        });
    }
});

