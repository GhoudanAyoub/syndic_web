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
                url : '/api/categories/syndic/' + $("#syndicId").val(),
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
                    var libelle = $("#libelle").val();

                    if (libelle == "") {
                        $("#libelle").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#libelle").css("border", "1px solid #d4d4d4");
                    }

                    if (verif) {
                        var json = {
                            libelle : libelle,
                            syndic : {id : $("#syndicId").val()}
                        };

                        $.ajax({
                            url : '/api/categories',
                            contentType : 'application/json',
                            data : JSON.stringify(json),
                            type : 'POST',
                            async : false,
                            success : function(data,
                                               textStatus, jqXHR) {
                                $.ajax({
                                    url : '/api/categories/syndic/' + $("#syndicId").val(),
                                    type : 'GET',
                                    async : false,
                                    success : function(data,
                                                       textStatus, jqXHR) {
                                        remplir(data);
                                        $("#categorieId").val("");
                                        $("#libelle").val("");
                                        swal("Succès!", "Ajout de la catégorie avec succès!", "success");
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
                                swal("Echec!", "Echec lors de l'ajout de la catégorie!", "warning");
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

    $("#search").click(function() {
        if($("#categorieRech").val() != "") {
            $.ajax({
                url : '/api/categories/syndic/libelle/' + $("#syndicId").val() + '/' +$("#categorieRech").val(),
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
        }
    });

    $("#annulerRech").click(function() {
        $("#categorieRech").val("");
        $.ajax({
            url : '/api/categories/syndic/' + $("#syndicId").val(),
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

    function remplir(data) {
        var ligne = "";
        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                ligne += '<tr><td class="text-center">' + data[i].libelle + '</td><td class="text-center"><div class="dropdown"><a class="btn btn-link font-24 p-0 line-height-1 no-arrow dropdown-toggle" href="#" role="button" data-toggle="dropdown"><i class="dw dw-more"></i></a><div class="dropdown-menu dropdown-menu-right dropdown-menu-icon-list"><a class="dropdown-item btn-update" data-categorie=\'' + JSON.stringify(data[i]) + '\' href="javascript:void(0)"><i class="dw dw-edit2"></i> Modifier</a><a class="dropdown-item btn-delete" data-id="' + data[i].id + '" href="javascript:void(0)"><i class="dw dw-delete-3"></i> Supprimer</a></div></td></tr>';
            }
        }else {
            ligne = '<td colspan="2" align="center"><p class="fs-2">Pas de catégories !<p></td></tr>';
        }
        $("#table").html(ligne);

        $(".btn-delete").click(function() {
            swal({
                title: "Voulez-vous supprimer cette catégorie?",
                icon: "info",
                buttons: true,
                showcancelbutton: true,
            })
                .then((isConfirm) => {
                    if (isConfirm) {
                        var delid = $(this).data("id");
                        $.ajax({
                            url : '/api/categories/' + delid,
                            contentType : 'application/json',
                            type : 'DELETE',
                            async : false,
                            success : function(data,
                                               textStatus, jqXHR) {
                                $.ajax({
                                    url : '/api/categories/syndic/' + $("#syndicId").val(),
                                    type : 'GET',
                                    async : false,
                                    success : function(data,
                                                       textStatus, jqXHR) {
                                        remplir(data);
                                        swal("Succès!", "Suppression de la catégorie avec succès!", "success");
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
                                swal("Echec!", "Echec lors de la suppression de la catégorie!", "warning");
                            }
                        });
                    }
                });
        });

        $(".btn-update").click(function() {
            var categorie = $(this).data("categorie");

            $("#categorieId").val(categorie.id);
            $("#libelle").val(categorie.libelle);

            $("#ajouter").prop('value', 'Modifier');
            $("#divannuler").prop('hidden', false);

            $("#annuler").click(function() {
                $("#ajouter").prop('value', 'Ajouter');
                $("#categorieId").val("");
                $("#libelle").val("");
                $("#divannuler").prop('hidden', true);
            });

            $("#ajouter").click(function(e) {
                if($(this).attr("value") == "Modifier") {
                    e.preventDefault();
                    var verif = true;
                    var categorieId = $("#categorieId").val();
                    var libelle = $("#libelle").val();

                    if (libelle == "") {
                        $("#libelle").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#libelle").css("border", "1px solid #d4d4d4");
                    }

                    if (verif) {
                        var json = {
                            libelle : libelle
                        };

                        $.ajax({
                            url : '/api/categories/' + categorieId,
                            contentType : 'application/json',
                            data : JSON.stringify(json),
                            type : 'PUT',
                            async : false,
                            success : function(data,
                                               textStatus, jqXHR) {
                                $.ajax({
                                    url : '/api/categories/syndic/' + $("#syndicId").val(),
                                    type : 'GET',
                                    async : false,
                                    success : function(data,
                                                       textStatus, jqXHR) {
                                        remplir(data);
                                        $("#ajouter").prop('value', 'Ajouter');
                                        $("#categorieId").val("");
                                        $("#libelle").val("");
                                        $("#divannuler").prop('hidden', true);
                                        swal("Succès!", "Modification de la catégorie avec succès!", "success");
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
                                swal("Echec!", "Echec lors de la modification de la catégorie!", "warning");
                            }
                        });
                    }
                }
            });

        });
    }
});

