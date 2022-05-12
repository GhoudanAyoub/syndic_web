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
                url : '/api/revenus/',
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
                    var appartement= $("#appartement").val();



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
                            appartement:{id :appartement},
                        };

                        $.ajax({
                            url : '/api/revenus',
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
                                swal("Succès!", "Ajout du revenu avec succès!", "success");
                            },
                            error : function(jqXHR, textStatus,
                                             errorThrown) {
                                console.log(textStatus, errorThrown);
                                swal("Echec!", "Echec lors de l'ajout de la revenu!", "warning");
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
        $.ajax({
            url : '/api/revenus/',
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

        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                ligne += '<tr><td class="text-center">' + data[i].montant + '</td><td class="text-center">' + moment(data[i].date).format('YYYY-MM-DD') + '</td><td class="text-center">' + data[i].description + '</td><td class="text-center">' + data[i].immeuble.nom + '</td><td class="text-center">' + data[i].appartement.numero + '</td><td class="text-center"><div class="dropdown"><a class="btn btn-link font-24 p-0 line-height-1 no-arrow dropdown-toggle" href="#" role="button" data-toggle="dropdown"><i class="dw dw-more"></i></a><div class="dropdown-menu dropdown-menu-right dropdown-menu-icon-list"><a class="dropdown-item btn-update" data-revenu=\'' + JSON.stringify(data[i]) + '\' href="javascript:void(0)"><i class="dw dw-edit2"></i> Modifier</a><a class="dropdown-item btn-delete" data-id="' + data[i].id + '" href="javascript:void(0)"><i class="dw dw-delete-3"></i> Supprimer</a></div></td></tr>';
            }
        }
        if(datim.length>0){
            for (var i = 0; i < datim.length; i++) {
                comboim += '<option value="'+datim[i].id+'">'+datim[i].nom+'</option>';
            }
        }
        $("#table").html(ligne);
        $("#immeuble").html(comboim);
        function combappartement(idappa) {
            var data;
            $.ajax({
                url : '/api/appartementByImmeuble/'+idappa,
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
            var ligne = "";

            if(data.length>0){
                for (var i = 0; i < data.length; i++) {
                    ligne += '<option value="'+data[i].id+'">'+data[i].numero+'</option>';
                }
            }
            $("#appartement").html(ligne);
        }


        $(".btn-delete").click(function() {
            swal({
                title: "Voulez-vous supprimer cet revenue?",
                icon: "info",
                buttons: true,
                showcancelbutton: true,
            })
                .then((isConfirm) => {
                    if (isConfirm) {
                        var delid = $(this).data("id");
                        $.ajax({
                            url : '/api/revenus/' + delid,
                            contentType : 'application/json',
                            type : 'DELETE',
                            async : false,
                            success : function(data,
                                               textStatus, jqXHR) {
                                remplir();
                                swal("Succès!", "Suppression de la revenus avec succès!", "success");
                            },
                            error : function(jqXHR, textStatus,
                                             errorThrown) {
                                console.log(textStatus, errorThrown);
                                swal("Echec!", "Echec lors de la suppression de la revenus!", "warning");
                            }
                        });
                    }
                });
        });

        $(".btn-update").click(function() {
            var revenu = $(this).data("revenu");

            $("#montant").val(revenu.montant);
            $("#date").val(moment(revenu.date).format('YYYY-MM-DD'));
            $("#description").val(revenu.description);
            $("#immeuble").val(revenu.immeuble.id).change();
            $("#apartement").val(revenu.appartement.id).change();


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
                if($(this).attr("value") =="Modifier") {
                    e.preventDefault();
                    var verif = true;
                    var montant = $("#montant").val();
                    var date = $("#date").val();
                    var description = $("#description").val();
                    var immeuble= $("#immeuble").val();
                    var appartement= $("#appartement").val();



                    if (montant =="") {
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
                            appartement:{id :appartement},

                        };

                        $.ajax({
                            url : '/api/revenus/' + revenu.id,
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
                                swal("Succès!", "Modification de la revenu avec succès!", "success");
                            },
                            error : function(jqXHR, textStatus,
                                             errorThrown) {
                                console.log(textStatus, errorThrown);
                                swal("Echec!", "Echec lors de la modification de la revenu!", "warning");
                            }
                        });

                    }
                }
            });

        });
    }
    function combappartement() {
        var idappa = $("#immeuble").val();
        var data;
        console.log('khedama');
        $.ajax({
            url : '/api/appartementByImmeuble/'+idappa,
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
        var ligne = "";

        if(data.length>0){
            for (var i = 0; i < data.length; i++) {
                ligne += '<option value="'+data[i].id+'">'+data[i].numero+'</option>';
            }
        }
        $("#appartement").html(ligne);
    }
});
