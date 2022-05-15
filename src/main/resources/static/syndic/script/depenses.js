$(document).ready(function() {
    $.ajax({
        url : '/api/sessions',
        type : 'GET',
        async : false,
        success : function(data,
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
                url : '/api/immeubles/syndic/' + $("#syndicId").val(),
                type : 'GET',
                async : false,
                success : function(data,
                                   textStatus, jqXHR) {
                    remplirImmeuble(data);
                },
                error : function(jqXHR, textStatus,
                                 errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });

            $.ajax({
                url : '/api/categories/syndic/' + $("#syndicId").val(),
                type : 'GET',
                async : false,
                success : function(data,
                                   textStatus, jqXHR) {
                    remplirCategorie(data);
                },
                error : function(jqXHR, textStatus,
                                 errorThrown) {
                    console.log(textStatus, errorThrown);
                }
            });

            $.ajax({
                url : '/api/depenses/syndic/' + $("#syndicId").val(),
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
                    var immeubleId = $("#immeuble").val();
                    var categorieId = $("#categorie").val();
                    var montant = $("#montant").val();
                    var date = $("#date").val();
                    var description = $("#description").val();

                    if (montant == "") {
                        $("#montant").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#montant").css("border", "1px solid #d4d4d4");
                    }

                    if (description == "") {
                        $("#description").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#description").css("border", "1px solid #d4d4d4");
                    }

                    if (verif) {
                        var json = {
                            immeuble : {id: immeubleId},
                            categorie : {id : categorieId},
                            montant : montant,
                            date : date,
                            description : description
                        };

                        $.ajax({
                            url : '/api/depenses',
                            contentType : 'application/json',
                            data : JSON.stringify(json),
                            type : 'POST',
                            async : false,
                            success : function(data,
                                               textStatus, jqXHR) {
                                $.ajax({
                                    url : '/api/depenses/syndic/' + $("#syndicId").val(),
                                    type : 'GET',
                                    async : false,
                                    success : function(data,
                                                       textStatus, jqXHR) {
                                        remplir(data);
                                        $("#depenseId").val("");
                                        $("#montant").val("");
                                        $("#description").val("");
                                        swal("Succès!", "Ajout de la dépense avec succès!", "success");
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
                                swal("Echec!", "Echec lors de l'ajout de la dépense!", "warning");
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

    $("#search").click(function () {
        $.ajax({
            url: '/api/depenseByImmeuble/' + $("#immeubleRech").val(),
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
            url : '/api/depenses/syndic/' + $("#syndicId").val(),
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

    function remplirImmeuble(data) {
        var ligne = "";
        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                ligne += '<option value="' + data[i].id + '">' + data[i].nom + '</option>';
            }
        }
        $("#immeuble").html(ligne);
        $("#immeubleRech").html(ligne);
    }

    function remplirCategorie(data) {
        var ligne = "";
        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                ligne += '<option value="' + data[i].id + '">' + data[i].libelle + '</option>';
            }
        }
        $("#categorie").html(ligne);
    }

    function remplir(data) {
        var ligne = "";
        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                ligne += '<tr><td class="text-center">' + data[i].immeuble.nom + '</td><td class="text-center">' + data[i].categorie.libelle + '</td><td class="text-center">' + data[i].montant + '</td><td class="text-center">' + moment(data[i].date).format("YYYY-MM-DD") + '</td><td class="text-center">' + data[i].description + '</td><td class="text-center"><div class="dropdown"><a class="btn btn-link font-24 p-0 line-height-1 no-arrow dropdown-toggle" href="#" role="button" data-toggle="dropdown"><i class="dw dw-more"></i></a><div class="dropdown-menu dropdown-menu-right dropdown-menu-icon-list"><a class="dropdown-item btn-update" data-depense=\'' + JSON.stringify(data[i]) + '\' href="javascript:void(0)"><i class="dw dw-edit2"></i> Modifier</a><a class="dropdown-item btn-delete" data-id="' + data[i].id + '" href="javascript:void(0)"><i class="dw dw-delete-3"></i> Supprimer</a></div></td></tr>';
            }
        }else {
            ligne = '<td colspan="6" align="center"><p class="fs-2">Pas de dépenses !<p></td></tr>';
        }
        $("#table").html(ligne);

        $(".btn-delete").click(function() {
            swal({
                title: "Voulez-vous supprimer cette dépense?",
                icon: "info",
                buttons: true,
                showcancelbutton: true,
            })
                .then((isConfirm) => {
                    if (isConfirm) {
                        var delid = $(this).data("id");
                        $.ajax({
                            url : '/api/depenses/' + delid,
                            contentType : 'application/json',
                            type : 'DELETE',
                            async : false,
                            success : function(data,
                                               textStatus, jqXHR) {
                                $.ajax({
                                    url : '/api/depenses/syndic/' + $("#syndicId").val(),
                                    type : 'GET',
                                    async : false,
                                    success : function(data,
                                                       textStatus, jqXHR) {
                                        remplir(data);
                                        swal("Succès!", "Suppression de la dépense avec succès!", "success");
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
                                swal("Echec!", "Echec lors de la suppression de la dépense!", "warning");
                            }
                        });
                    }
                });
        });

        $(".btn-update").click(function() {
            var depense = $(this).data("depense");

            $("#depenseId").val(depense.id);
            $("#immeuble").val(depense.immeuble.id);
            $("#immeuble").change();
            $("#categorie").val(depense.categorie.id);
            $("#categorie").change();
            $("#montant").val(depense.montant);
            $("#date").val(moment(depense.date).format("YYYY-MM-DD"));
            $("#description").val(depense.description);

            $("#ajouter").prop('value', 'Modifier');
            $("#divannuler").prop('hidden', false);

            $("#annuler").click(function() {
                $("#ajouter").prop('value', 'Ajouter');
                $("#depenseId").val("");
                $("#montant").val("");
                $("#description").val("");
            });

            $("#ajouter").click(function(e) {
                if($(this).attr("value") == "Modifier") {
                    e.preventDefault();
                    var verif = true;
                    var depenseId = $("#depenseId").val();
                    var immeubleId = $("#immeuble").val();
                    var categorieId = $("#categorie").val();
                    var montant = $("#montant").val();
                    var date = $("#date").val();
                    var description = $("#description").val();

                    if (montant == "") {
                        $("#montant").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#montant").css("border", "1px solid #d4d4d4");
                    }

                    if (description == "") {
                        $("#description").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#description").css("border", "1px solid #d4d4d4");
                    }

                    if (verif) {
                        var json = {
                            immeuble : {id: immeubleId},
                            categorie : {id : categorieId},
                            montant : montant,
                            date : date,
                            description : description
                        };

                        $.ajax({
                            url : '/api/depenses/' + depenseId,
                            contentType : 'application/json',
                            data : JSON.stringify(json),
                            type : 'PUT',
                            async : false,
                            success : function(data,
                                               textStatus, jqXHR) {
                                $.ajax({
                                    url : '/api/depenses/syndic/' + $("#syndicId").val(),
                                    type : 'GET',
                                    async : false,
                                    success : function(data,
                                                       textStatus, jqXHR) {
                                        remplir(data);
                                        $("#ajouter").prop('value', 'Ajouter');
                                        $("#depenseId").val("");
                                        $("#montant").val("");
                                        $("#description").val("");;
                                        swal("Succès!", "Modification de la dépense avec succès!", "success");
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
                                swal("Echec!", "Echec lors de la modification de la dépense!", "warning");
                            }
                        });
                    }
                }
            });

        });
    }
});

