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
                url : '/api/depenses/',
                type : 'GET',
                async : false,
                success : function(data,
                                   textStatus, jqXHR) {
                    remplir();
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
                    var montant = $("#montant").val();
                    var date = $("#date").val();
                    var description = $("#description").val();
                    var immeuble= $("#immeuble").val();
                    var categorie= $("#categorie").val();



                    if (montant == "") {
                        $("#montant").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#montant").css("border", "1px solid #d4d4d4");
                    }
                    if (date == "") {
                        $("#date").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#date").css("border", "1px solid #d4d4d4");
                    }
                    if (description == "") {
                        $("#description").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#description").css("border", "1px solid #d4d4d4");
                    }
                    if (verif) {
                        var json = {
                            montant : montant,
                            date: date,
                            description: description,
                            immeuble : {id : immeuble} ,
                            categorie:{id :categorie},
                        };

                        $.ajax({
                            url : '/api/depenses',
                            contentType : 'application/json',
                            data : JSON.stringify(json),
                            type : 'POST',
                            async : false,
                            success : function(data,
                                               textStatus, jqXHR) {
                                remplir();
                                $("#montant").val("");
                                $("#date").val("");
                                $("#description").val("");
                                swal("Succès!", "Ajout du depense avec succès!", "success");
                            },
                            error : function(jqXHR, textStatus,
                                             errorThrown) {
                                console.log(textStatus, errorThrown);
                                swal("Echec!", "Echec lors de l'ajout de la depense!", "warning");
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

    function remplir() {
        var data;
        var datim;
        var datcat;
        $.ajax({
            url : '/api/depenses/',
            type : 'GET',
            async : false,
            success : function(datas,
                               textStatus, jqXHR) {
                data=datas;
            },
            error : function(jqXHR, textStatus,
                             errorThrown) {
                console.log(textStatus, errorThrown);
            }
        });
        $.ajax({
            url : '/api/categories/',
            type : 'GET',
            async : false,
            success : function(datas,
                               textStatus, jqXHR) {
                datcat=datas;
            },
            error : function(jqXHR, textStatus,
                             errorThrown) {
                console.log(textStatus, errorThrown);
            }
        });
        $.ajax({
            url : '/api/immeubles/',
            type : 'GET',
            async : false,
            success : function(datai,
                               textStatus, jqXHR) {
                datim=datai;
            },
            error : function(jqXHR, textStatus,
                             errorThrown) {
                console.log(textStatus, errorThrown);
            }
        });
        var ligne = "";
        var comboim ="";
        var combocat="";

        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                ligne += '<tr><td class="text-center">' + data[i].montant + '</td><td class="text-center">' + data[i].date + '</td><td class="text-center">' + data[i].description + '</td><td class="text-center">' + data[i].immeuble.nom + '</td><td class="text-center">' + data[i].categorie.libelle + '</td><td class="text-center"><div class="dropdown"><a class="btn btn-link font-24 p-0 line-height-1 no-arrow dropdown-toggle" href="#" role="button" data-toggle="dropdown"><i class="dw dw-more"></i></a><div class="dropdown-menu dropdown-menu-right dropdown-menu-icon-list"><a class="dropdown-item btn-update" data-depense=\'' + JSON.stringify(data[i]) + '\' href="javascript:void(0)"><i class="dw dw-edit2"></i> Modifier</a><a class="dropdown-item btn-delete" data-id="' + data[i].id + '" href="javascript:void(0)"><i class="dw dw-delete-3"></i> Supprimer</a></div></td></tr>';
            }
        }
        if(datim.length>0){
            for (var i = 0; i < datim.length; i++) {
                comboim += '<option value="'+datim[i].id+'">'+datim[i].nom+'</option>';
            }
        }
        if(datcat.length>0){
            for (var i = 0; i < datcat.length; i++) {
                combocat += '<option value="'+datcat[i].id+'">'+datcat[i].libelle+'</option>';
            }
        }

        $("#table").html(ligne);
        $("#immeuble").html(comboim);
        $("#categorie").html(combocat);


        $(".btn-delete").click(function() {
            swal({
                title: "Voulez-vous supprimer cet depense?",
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
                                remplir();
                                swal("Succès!", "Suppression de la depense avec succès!", "success");
                            },
                            error : function(jqXHR, textStatus,
                                             errorThrown) {
                                console.log(textStatus, errorThrown);
                                swal("Echec!", "Echec lors de la suppression de la depense!", "warning");
                            }
                        });
                    }
                });
        });

        $(".btn-update").click(function() {
            var depense = $(this).data("depense");

            $("#montant").val(depense.montant);
            $("#date").val(moment(depense.date).format('YYYY-MM-DD'));
            $("#description").val(depense.description);
            $("#immeuble").val(depense.immeuble.id).change();
            $("#categorie").val(depense.categorie.id).change();


            $("#ajouter").prop('value', 'Modifier');
            $("#divannuler").prop('hidden', false);

            $("#annuler").click(function() {
                $("#ajouter").prop('value', 'Ajouter');
                $("#montant").val("");
                $("#date").val("");
                $("#description").val("");

                $("#divannuler").prop('hidden', true);
                deleted = false;
                uploaded = false;
            });

            $("#ajouter").click(function(e) {
                if($(this).attr("value") == "Modifier") {
                    e.preventDefault();
                    var verif = true;
                    var montant = $("#montant").val();
                    var date = $("#date").val();
                    var description = $("#description").val();
                    var immeuble= $("#immeuble").val();
                    var categorie= $("#categorie").val();



                    if (montant == "") {
                        $("#montant").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#montant").css("border", "1px solid #d4d4d4");
                    }
                    if (date == "") {
                        $("#date").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#date").css("border", "1px solid #d4d4d4");
                    }
                    if (description == "") {
                        $("#description").css("border", "1px solid red");
                        verif = false;
                    } else {
                        $("#description").css("border", "1px solid #d4d4d4");
                    }

                    if (verif) {

                        var json = {
                            montant : montant,
                            date: date,
                            description: description,
                            immeuble : {id : immeuble} ,
                            categorie:{id :categorie},

                        };

                        $.ajax({
                            url : '/api/depenses/' + depense.id,
                            contentType : 'application/json',
                            data : JSON.stringify(json),
                            type : 'PUT',
                            async : false,
                            success : function(data,
                                               textStatus, jqXHR) {
                                remplir();
                                $("#ajouter").prop('value', 'Ajouter');
                                $("#montant").val("");
                                $("#date").val("");
                                $("#description").val("");
                                $("#divannuler").prop('hidden', true);
                                deleted = false;
                                uploaded = false;
                                swal("Succès!", "Modification de la depense avec succès!", "success");
                            },
                            error : function(jqXHR, textStatus,
                                             errorThrown) {
                                console.log(textStatus, errorThrown);
                                swal("Echec!", "Echec lors de la modification de la depense!", "warning");
                            }
                        });

                    }
                }
            });

        });
    }
});
