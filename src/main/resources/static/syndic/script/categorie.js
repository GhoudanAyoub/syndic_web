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
                url : '/api/categories/',
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

                        };

                        $.ajax({
                            url : '/api/categories',
                            contentType : 'application/json',
                            data : JSON.stringify(json),
                            type : 'POST',
                            async : false,
                            success : function(data,
                                               textStatus, jqXHR) {
                                remplir();
                                swal("Succès!", "Ajout du categorie avec succès!", "success");
                            },
                            error : function(jqXHR, textStatus,
                                             errorThrown) {
                                console.log(textStatus, errorThrown);
                                swal("Echec!", "Echec lors de l'ajout de la categorie!", "warning");
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
        $.ajax({
            url : '/api/categories/',
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
        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                ligne += '<tr><td class="text-center">' + data[i].libelle + '</td><td class="text-center"><div class="dropdown"><a class="btn btn-link font-24 p-0 line-height-1 no-arrow dropdown-toggle" href="#" role="button" data-toggle="dropdown"><i class="dw dw-more"></i></a><div class="dropdown-menu dropdown-menu-right dropdown-menu-icon-list"><a class="dropdown-item btn-update" data-categorie=\'' + JSON.stringify(data[i]) + '\' href="javascript:void(0)"><i class="dw dw-edit2"></i> Modifier</a><a class="dropdown-item btn-delete" data-id="' + data[i].id + '" href="javascript:void(0)"><i class="dw dw-delete-3"></i> Supprimer</a></div></td></tr>';
            }
        }
        $("#table").html(ligne);

        $(".btn-delete").click(function() {
            swal({
                title: "Voulez-vous supprimer cet categorie?",
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
                                remplir();
                                swal("Succès!", "Suppression de la categorie avec succès!", "success");
                            },
                            error : function(jqXHR, textStatus,
                                             errorThrown) {
                                console.log(textStatus, errorThrown);
                                swal("Echec!", "Echec lors de la suppression de la categorie!", "warning");
                            }
                        });
                    }
                });
        });

        $(".btn-update").click(function() {
            var categorie = $(this).data("categorie");

            $("#libelle").val(categorie.libelle);


            $("#ajouter").prop('value', 'Modifier');
            $("#divannuler").prop('hidden', false);

            $("#annuler").click(function() {
                $("#ajouter").prop('value', 'Ajouter');
                $("#libelle").val("");

                $("#divannuler").prop('hidden', true);
                deleted = false;
                uploaded = false;
            });

            $("#ajouter").click(function(e) {
                if($(this).attr("value") == "Modifier") {
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

                        };

                        $.ajax({
                            url : '/api/categories/' + categorie.id,
                            contentType : 'application/json',
                            data : JSON.stringify(json),
                            type : 'PUT',
                            async : false,
                            success : function(data,
                                               textStatus, jqXHR) {
                                remplir();
                                $("#ajouter").prop('value', 'Ajouter');
                                $("#libelle").val("");
                                $("#divannuler").prop('hidden', true);
                                deleted = false;
                                uploaded = false;
                                swal("Succès!", "Modification de la categorie avec succès!", "success");
                            },
                            error : function(jqXHR, textStatus,
                                             errorThrown) {
                                console.log(textStatus, errorThrown);
                                swal("Echec!", "Echec lors de la modification de la categorie!", "warning");
                            }
                        });

                    }
                }
            });

        });
    }
});
