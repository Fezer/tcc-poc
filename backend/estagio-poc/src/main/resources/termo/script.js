$(document).ready(function(){
   $('.cpf').mask('000.000.000-00' , {reverse : true}) ;
   $('.horas').mask('00:00' , {reverse : true}) ;
   $('.tel').mask('(00) 999999999', { placeholder: "(xx) xxxxxxxx" }) ;
   $('.cnpj').mask('00.000.000/0000-00', { placeholder: "xx.xxx.xxx/xxxx-xx" }) ;
   $('.cep').mask('00000-000', { placeholder: "xxxx-xxx" }) ;
   $('.dinheiro').mask('000.000.000.000.000,00' , { reverse : true}) ;
   $('.grr').mask('GRR00000000', { placeholder: "GRR00000000" }) ;
});

function validateForm() {
    //esta função verifica se o formulário foi validado pelo bootstrapValidator
    //se o retorno da validação for true, quer dizer que o formulario foi validado
    //e atende aos requisitos para gerar o documento em pdf, então
    //a funão validateForm chama o modal com avisos ou instruções antes da geração do documento em pdf
    $('.form').data('bootstrapValidator').validate();
    var tes = $('.form').data('bootstrapValidator').isValid();
    if(tes){
        $('#myModal').modal('toggle');
    }
};

function message(){
    //quando o usuario clicar no ok do modal que vai aparecer após o
    //preenchimento correto do formulario, esta funcão será chamada e
    //e ela chamará o submit do formulário para gerar o documento pdf
    $(".form").data('bootstrapValidator').defaultSubmit();
};


$(document).ready(function() {

    $('#idmodalidade_estagio_nova').change(function(){
        if($(this).val() == "Não Obrigatório"){
            $('#idnumero_apolice_seguro').val("");            
            $('#idnumero_apolice_seguro').attr('readonly', false);
            $('#idempresa_seguradora').val("");
            $('#idempresa_seguradora').attr('readonly', false);
        }else{
            $('#idnumero_apolice_seguro').val("");            
            $('#idnumero_apolice_seguro').attr('readonly', false);
            $('#idempresa_seguradora').val("");            
            $('#idempresa_seguradora').attr('readonly', false);
        }
    }); 

    $('.form').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedback: {
            valid: 'glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            nome_aluno: {
                validators: {
                    notEmpty: {
                        message: 'Por favor insira o nome Completo'
                    },
                        stringLength: {
                        min: 3
                    }
                        
                }
            },
            nome_emp_estagUFPR: {
                validators: {
                    notEmpty: {
                        message: 'Por favor insira o nome Completo'
                    },
                    stringLength: {
                    min: 3
                    }
                }
            },
            cep_emp_estag: {
                validators: {
                    notEmpty: {
                        message: 'Por favor forneça um CEP'
                    },
                    zipCode: {
                        country: 'BR',
                        message: 'Por favor forneça um CEP válido'
                    }
                }
            },			
            tel_empUFPR: {
                validators: {
                    notEmpty: {
                        message: 'Por favor forneça um telefone de contato'
                    },
                    phone: {
                        country: 'BR',
                        message: 'Por favor forneça um telefone válido com DDD'
                    }
                }
            },
            rua_empUFPR: {
                validators: {
                     stringLength: {
                        min: 8
                    },
                    notEmpty: {
                        message: 'Por favor insira o nome da sua rua'
                    }
                }
            },
            cidade_empUFPR: {
                validators: {
                     stringLength: {
                        min: 3
                    },
                    notEmpty: {
                        message: 'Por favor forneça o nome da cidade'
                    }
                }
            },
            cnpj_empUFPR:{
                validators: {
                    notEmpty: {
                        message: 'Por favor coloque o CNPJ'
                    },
                    vat:{
                        country: 'BR',
                        message: 'Por favor coloque um CNPJ válido'
                    }
                }    
            },
            nome_repre_emprUFPR: {
                validators: {
                    notEmpty: {
                        message: 'Por favor insira o nome Completo'
                    },
                    stringLength: {
                    min: 3
                    }
                }
            },
            numero_rua_empUFPR: {
                validators: {
                    notEmpty: {
                        message: 'Por favor insira um Número completo'
                    },
                    stringLength: {
                    min: 1
                    }
                }
            },
            nome_emp_estag: {
                validators: {
                    notEmpty: {
                        message: 'Por favor insira o nome Completo'
                    },
                    stringLength: {
                    min: 3
                    }
                }
            },
            rua_contratante: {
                 validators: {
                     stringLength: {
                        min: 3
                    },
                    notEmpty: {
                        message: 'Por favor forneça o nome da cidade'
                    }
                }
            },
            numero_rua_contratante: {
                validators: {
                    notEmpty: {
                        message: 'Por favor insira um Número completo'
                    },
                    stringLength: {
                    min: 1
                    }
                }
            },
            cep_contratante_estag: {
                validators: {
                    notEmpty: {
                        message: 'Por favor forneça um CEP'
                    },
                    zipCode: {
                        country: 'BR',
                        message: 'Por favor forneça um CEP válido'
                    }
                }
            },
            cidade_contratante: {
                 validators: {
                     stringLength: {
                        min: 3
                    },
                    notEmpty: {
                        message: 'Por favor forneça o nome da cidade'
                    }
                }
            },
            nome_repre_empr: {
                validators: {
                    notEmpty: {
                        message: 'Por favor insira o nome Completo'
                    },
                    stringLength: {
                    min: 3
                    }
                }
            },
            numero_rua_emp: {
                validators: {
                    notEmpty: {
                        message: 'Por favor insira um Número completo'
                    },
                    stringLength: {
                    min: 1
                    }
                }
            },
            modalidade_estagio_antiga:{
                validators:{
                    callback:{
                        callback: function(value, validator){
                            var nova = $("#idmodalidade_estagio_nova").val();
                            var antiga = value;
                            
                            if(nova === "" && antiga === ""){
                                return{
                                    valid: true
                                };
                            }if((nova !== "" && antiga === "") || (nova === "" && antiga !== "")){
                                return{
                                    valid: false,
                                    message: "As duas modalidades precisam estar preenchidas ou não"
                                };
                            }else{
                                return{
                                    valid: true
                                };
                            }
                        }   
                    }
                }
            },
            modalidade_estagio_nova:{
                validators:{
                    callback:{
                        callback: function(value, validator){
                            var antiga = $("#idmodalidade_estagio_antiga").val();
                            var nova = value;
                            
                            if(nova === "" && antiga === ""){
                                return{
                                    valid: true
                                };
                            }if((nova !== "" && antiga === "") || (nova === "" && antiga !== "")){
                                return{
                                    valid: false,
                                    message: "As duas modalidades precisam estar preenchidas ou não"
                                };
                            }else{
                                return{
                                    valid: true
                                };
                            }
                        }   
                    }
                }
            },
            grau_parentesco1:{
                validators:{
                    callback:{
                        callback: function(value, validator){
                            var nome_parente = $("#idnome_parente1").val();
                            var grau_parentesco = value;
                            
                            if(nome_parente === "" && grau_parentesco === ""){
                                return{
                                    valid: true
                                };
                            }if((nome_parente !== "" && grau_parentesco === "") || (nome_parente === "" && grau_parentesco !== "")){
                                return{
                                    valid: false,
                                    message: "Os dois campos precisam estar preenchidas ou não!"
                                };
                            }else{
                                return{
                                    valid: true
                                };
                            }
                        }   
                    }
                }
            },
            nome_parente1:{
                validators:{
                    callback:{
                        callback: function(value, validator){
                            var grau_parentesco = $("#idgrau_parentesco1").val();
                            var nome_parente = value;
                            
                            if(grau_parentesco === "" && nome_parente === ""){
                                return{
                                    valid: true
                                };
                            }if((grau_parentesco !== "" && nome_parente === "") || (grau_parentesco === "" && nome_parente !== "")){
                                return{
                                    valid: false,
                                    message: "Os dois campos precisam estar preenchidas ou não!"
                                };
                            }else{
                                return{
                                    valid: true
                                };
                            }
                        }   
                    }
                }
            },
            grau_parentesco2:{
                validators:{
                    callback:{
                        callback: function(value, validator){
                            var nome_parente = $("#idnome_parente2").val();
                            var grau_parentesco = value;
                            
                            if(nome_parente === "" && grau_parentesco === ""){
                                return{
                                    valid: true
                                };
                            }if((nome_parente !== "" && grau_parentesco === "") || (nome_parente === "" && grau_parentesco !== "")){
                                return{
                                    valid: false,
                                    message: "Os dois campos precisam estar preenchidas ou não!"
                                };
                            }else{
                                return{
                                    valid: true
                                };
                            }
                        }   
                    }
                }
            },
            nome_parente2:{
                validators:{
                    callback:{
                        callback: function(value, validator){
                            var grau_parentesco = $("#idgrau_parentesco2").val();
                            var nome_parente = value;
                            
                            if(grau_parentesco === "" && nome_parente === ""){
                                return{
                                    valid: true
                                };
                            }if((grau_parentesco !== "" && nome_parente === "") || (grau_parentesco === "" && nome_parente !== "")){
                                return{
                                    valid: false,
                                    message: "Os dois campos precisam estar preenchidas ou não!"
                                };
                            }else{
                                return{
                                    valid: true
                                };
                            }
                        }   
                    }
                }
            },
            grau_parentesco3:{
                validators:{
                    callback:{
                        callback: function(value, validator){
                            var nome_parente = $("#idnome_parente3").val();
                            var grau_parentesco = value;
                            
                            if(nome_parente === "" && grau_parentesco === ""){
                                return{
                                    valid: true
                                };
                            }if((nome_parente !== "" && grau_parentesco === "") || (nome_parente === "" && grau_parentesco !== "")){
                                return{
                                    valid: false,
                                    message: "Os dois campos precisam estar preenchidas ou não!"
                                };
                            }else{
                                return{
                                    valid: true
                                };
                            }
                        }   
                    }
                }
            },
            nome_parente3:{
                validators:{
                    callback:{
                        callback: function(value, validator){
                            var grau_parentesco = $("#idgrau_parentesco3").val();
                            var nome_parente = value;
                            
                            if(grau_parentesco === "" && nome_parente === ""){
                                return{
                                    valid: true
                                };
                            }if((grau_parentesco !== "" && nome_parente === "") || (grau_parentesco === "" && nome_parente !== "")){
                                return{
                                    valid: false,
                                    message: "Os dois campos precisam estar preenchidas ou não!"
                                };
                            }else{
                                return{
                                    valid: true
                                };
                            }
                        }   
                    }
                }
            },
            grau_parentesco4:{
                validators:{
                    callback:{
                        callback: function(value, validator){
                            var nome_parente = $("#idnome_parente4").val();
                            var grau_parentesco = value;
                            
                            if(nome_parente === "" && grau_parentesco === ""){
                                return{
                                    valid: true
                                };
                            }if((nome_parente !== "" && grau_parentesco === "") || (nome_parente === "" && grau_parentesco !== "")){
                                return{
                                    valid: false,
                                    message: "Os dois campos precisam estar preenchidas ou não!"
                                };
                            }else{
                                return{
                                    valid: true
                                };
                            }
                        }   
                    }
                }
            },
            nome_parente4:{
                validators:{
                    callback:{
                        callback: function(value, validator){
                            var grau_parentesco = $("#idgrau_parentesco4").val();
                            var nome_parente = value;
                            
                            if(grau_parentesco === "" && nome_parente === ""){
                                return{
                                    valid: true
                                };
                            }if((grau_parentesco !== "" && nome_parente === "") || (grau_parentesco === "" && nome_parente !== "")){
                                return{
                                    valid: false,
                                    message: "Os dois campos precisam estar preenchidas ou não!"
                                };
                            }else{
                                return{
                                    valid: true
                                };
                            }
                        }   
                    }
                }
            },
            grau_parentesco5:{
                validators:{
                    callback:{
                        callback: function(value, validator){
                            var nome_parente = $("#idnome_parente5").val();
                            var grau_parentesco = value;
                            
                            if(nome_parente === "" && grau_parentesco === ""){
                                return{
                                    valid: true
                                };
                            }if((nome_parente !== "" && grau_parentesco === "") || (nome_parente === "" && grau_parentesco !== "")){
                                return{
                                    valid: false,
                                    message: "Os dois campos precisam estar preenchidas ou não!"
                                };
                            }else{
                                return{
                                    valid: true
                                };
                            }
                        }   
                    }
                }
            },
            nome_parente5:{
                validators:{
                    callback:{
                        callback: function(value, validator){
                            var grau_parentesco = $("#idgrau_parentesco5").val();
                            var nome_parente = value;
                            
                            if(grau_parentesco === "" && nome_parente === ""){
                                return{
                                    valid: true
                                };
                            }if((grau_parentesco !== "" && nome_parente === "") || (grau_parentesco === "" && nome_parente !== "")){
                                return{
                                    valid: false,
                                    message: "Os dois campos precisam estar preenchidas ou não!"
                                };
                            }else{
                                return{
                                    valid: true
                                };
                            }
                        }   
                    }
                }
            },
            periodo_estagio_novo:{
                validators:{
                    callback: {
                        callback: function(fim, validator, $field){
                            
                            var ini = $("#idperiodo_estagio_antigo").val();
                            
                            var fim_estag = $("#idperiodo_estagio_novo").val();
                            
                            
                            if(fim_estag < fim && fim_estag !== ""){
                                return{
                                    valid: false,
                                    message: "A data final de recesso não pode ser maior que a data fim do estágio"
                                };
                            }
                            if(ini === "" && fim === ""){
                                return{
                                    valid: true
                                };
                            }if(ini !== "" && fim === ""){
                                return{
                                    valid: false,
                                    message: "A data final não pode estar em branco se a data inicial estiver preenchida"
                                };
                            }if(fim > ini && ini !== ""){
                                return{
                                    valid: true
                                };
                            }if(fim < ini){
                                return{
                                    valid: false,
                                    message: "A data final não pode ser menor que a data inicial"
                                };
                            }if(fim === ini){
                                return{
                                    valid: false,
                                    message: "As datas não podem ser iguais"
                                };
                            }if(fim > ini && ini === ""){
                                return{
                                    valid: false,
                                    message: 'A data inicial não pode estar em branco.'
                                };
                            }if(ini === ""){
                                return{
                                    valid: false
                                };
                            }
                        }
                    }
                }
            },
            periodo_estagio_antigo:{
                validators:{
                    callback: {
                        callback: function(ini, validator){
                            
                            var fim = $("#idperiodo_estagio_novo").val();
                            
                            
                            if(ini === "" && fim === ""){
                                return{
                                    valid: true
                                };
                            }if(fim !== "" && ini === ""){
                                return{
                                  valid: false,
                                    message: "A data inicial não pode estar em branco se a data final estiver preenchida"
                                };
                            }if(fim > ini && fim !== ""){
                                return{
                                    valid: true
                                };
                            }if(ini >= fim && fim === ""){
                                return{
                                    valid: false,
                                    message: "A data final não pode estar em branco."
                                };
                            }if(fim < ini){
                                return{
                                    valid: false,
                                    message: "A data inicial não pode ser maior que a data final"
                                };    
                            }if(ini < fim){
                                return{
                                    valid: true
                                };
                            }if(ini === fim){
                                return{
                                    valid: false,
                                    message: "As datas não podem ser iguais"
                                };
                            }
                        }
                    }
                }
            },
            primeiro_fim_periodo_recesso:{
                validators:{
                    callback: {
                        callback: function(fim, validator, $field){
                            
                            var ini = $("#idprimeiro_inicio_periodo_recesso").val();
                            
                            var fim_estag = $("#idfim_per_estag_aditivo").val();
                            
                            
                            if(fim_estag < fim && fim_estag !== ""){
                                return{
                                    valid: false,
                                    message: "A data final de recesso não pode ser maior que a data fim do estágio"
                                };
                            }
                            if(ini === "" && fim === ""){
                                return{
                                    valid: true
                                };
                            }if(ini !== "" && fim === ""){
                                return{
                                    valid: false,
                                    message: "A data final não pode estar em branco se a data inicial estiver preenchida"
                                };
                            }if(fim > ini && ini !== ""){
                                return{
                                    valid: true
                                };
                            }if(fim < ini){
                                return{
                                    valid: false,
                                    message: "A data final não pode ser menor que a data inicial"
                                };
                            }if(fim === ini){
                                return{
                                    valid: false,
                                    message: "As datas não podem ser iguais"
                                };
                            }if(fim > ini && ini === ""){
                                return{
                                    valid: false,
                                    message: 'A data inicial não pode estar em branco.'
                                };
                            }if(ini === ""){
                                return{
                                    valid: false
                                };
                            }
                        }
                    }
                }
            },
            primeiro_inicio_periodo_recesso:{
                validators:{
                    callback: {
                        callback: function(ini, validator){
                            
                            var fim = $("#idprimeiro_fim_periodo_recesso").val();
                            
                            
                            if(ini === "" && fim === ""){
                                return{
                                    valid: true
                                };
                            }if(fim !== "" && ini === ""){
                                return{
                                  valid: false,
                                    message: "A data inicial não pode estar em branco se a data final estiver preenchida"
                                };
                            }if(fim > ini && fim !== ""){
                                return{
                                    valid: true
                                };
                            }if(ini >= fim && fim === ""){
                                return{
                                    valid: false,
                                    message: "A data final não pode estar em branco."
                                };
                            }if(fim < ini){
                                return{
                                    valid: false,
                                    message: "a data inicial não pode ser maior que a data final"
                                };    
                            }if(ini < fim){
                                return{
                                    valid: true
                                };
                            }if(ini === fim){
                                return{
                                    valid: false,
                                    message: "As datas não podem ser iguais"
                                };
                            }
                        }
                    }
                }
            },
            segundo_fim_periodo_recesso:{
                validators:{
                    callback: {
                        callback: function(fim, validator, $field){
                            
                            var ini = $("#idsegundo_inicio_periodo_recesso").val();
                            
                            var fim_estag = $("#idfim_per_estag_aditivo").val();
                            
                            
                            if(fim_estag < fim && fim_estag !== ""){
                                return{
                                    valid: false,
                                    message: "A data final de recesso não pode ser maior que a data fim do estágio"
                                };
                            }
                            if(ini === "" && fim === ""){
                                return{
                                    valid: true
                                };
                            }if(ini !== "" && fim === ""){
                                return{
                                    valid: false,
                                    message: "A data final não pode estar em branco se a data inicial estiver preenchida"
                                };
                            }if(fim > ini && ini !== ""){
                                return{
                                    valid: true
                                };
                            }if(fim < ini){
                                return{
                                    valid: false,
                                    message: "A data final não pode ser menor que a data inicial"
                                };
                            }if(fim === ini){
                                return{
                                    valid: false,
                                    message: "As datas não podem ser iguais"
                                };
                            }if(fim > ini && ini === ""){
                                return{
                                    valid: false,
                                    message: 'A data inicial não pode estar em branco.'
                                };
                            }if(ini === ""){
                                return{
                                    valid: false
                                };
                            }
                        }
                    }
                }
            },
            segundo_inicio_periodo_recesso:{
                validators:{
                    callback: {
                        callback: function(ini, validator){
                            
                            var fim = $("#idsegundo_fim_periodo_recesso").val();
                            
                            
                            if(ini === "" && fim === ""){
                                return{
                                    valid: true
                                };
                            }if(fim !== "" && ini === ""){
                                return{
                                  valid: false,
                                    message: "A data inicial não pode estar em branco se a data final estiver preenchida"
                                };
                            }if(fim > ini && fim !== ""){
                                return{
                                    valid: true
                                };
                            }if(ini >= fim && fim === ""){
                                return{
                                    valid: false,
                                    message: "A data final não pode estar em branco."
                                };
                            }if(fim < ini){
                                return{
                                    valid: false,
                                    message: "a data inicial não pode ser maior que a data final"
                                };    
                            }if(ini < fim){
                                return{
                                    valid: true
                                };
                            }if(ini === fim){
                                return{
                                    valid: false,
                                    message: "As datas não podem ser iguais"
                                };
                            }
                        }
                    }
                }
            },
            terceiro_fim_periodo_recesso:{
                validators:{
                    callback: {
                        callback: function(fim, validator, $field){
                            
                            var ini = $("#idterceiro_inicio_periodo_recesso").val();
                            
                            var fim_estag = $("#idfim_per_estag_aditivo").val();
                            
                            
                            if(fim_estag < fim && fim_estag !== ""){
                                return{
                                    valid: false,
                                    message: "A data final de recesso não pode ser maior que a data fim do estágio"
                                };
                            }
                            if(ini === "" && fim === ""){
                                return{
                                    valid: true
                                };
                            }if(ini !== "" && fim === ""){
                                return{
                                    valid: false,
                                    message: "A data final não pode estar em branco se a data inicial estiver preenchida"
                                };
                            }if(fim > ini && ini !== ""){
                                return{
                                    valid: true
                                };
                            }if(fim < ini){
                                return{
                                    valid: false,
                                    message: "A data final não pode ser menor que a data inicial"
                                };
                            }if(fim === ini){
                                return{
                                    valid: false,
                                    message: "As datas não podem ser iguais"
                                };
                            }if(fim > ini && ini === ""){
                                return{
                                    valid: false,
                                    message: 'A data inicial não pode estar em branco.'
                                };
                            }if(ini === ""){
                                return{
                                    valid: false
                                };
                            }
                        }
                    }
                }
            },
            terceiro_inicio_periodo_recesso:{
                validators:{
                    callback: {
                        callback: function(ini, validator){
                            
                            var fim = $("#idterceiro_fim_periodo_recesso").val();
                            
                            
                            if(ini === "" && fim === ""){
                                return{
                                    valid: true
                                };
                            }if(fim !== "" && ini === ""){
                                return{
                                  valid: false,
                                    message: "A data inicial não pode estar em branco se a data final estiver preenchida"
                                };
                            }if(fim > ini && fim !== ""){
                                return{
                                    valid: true
                                };
                            }if(ini >= fim && fim === ""){
                                return{
                                    valid: false,
                                    message: "A data final não pode estar em branco."
                                };
                            }if(fim < ini){
                                return{
                                    valid: false,
                                    message: "a data inicial não pode ser maior que a data final"
                                };    
                            }if(ini < fim){
                                return{
                                    valid: true
                                };
                            }if(ini === fim){
                                return{
                                    valid: false,
                                    message: "As datas não podem ser iguais"
                                };
                            }
                        }
                    }
                }
            },
            ini_per_estag: {
                validators:{
                    callback: {
                        callback: function(ini, validator){
                            
                            var fim = $("#idfim_per_estag").val();
                            
                            var fullDate = new Date();

                            var twoDigitMonth = ((fullDate.getMonth().length+1) === 1)? (fullDate.getMonth()+1) : '0' + (fullDate.getMonth()+1);
                            
                            var day = fullDate.getDate();
                            
                            if(day <= 9){
                                day = "0" + day;
                            }

                            // var currentDate = fullDate.getFullYear()+ "-" + twoDigitMonth + "-" +day;
                            
                            // if(ini < currentDate ){
                            //     return{
                            //         valid: false,
                            //         message: "Por favor, a data de iníco deve ser maior ou igual a data de hoje"
                            //     };
                            // }
                            if(ini === "" && fim === ""){
                                return{
                                    valid: true
                                };
                            }if(fim !== "" && ini === ""){
                                return{
                                  valid: false,
                                    message: "A data inicial não pode estar em branco"
                                };
                            }if(fim > ini && fim !== ""){
                                return{
                                    valid: true
                                };
                            }if(ini >= fim && fim === ""){
                                return{
                                    valid: false,
                                    message: "A data final não pode estar em branco."
                                };
                            }if(fim < ini){
                                return{
                                    valid: false,
                                    message: "a data inicial não pode ser maior que a data final"
                                };    
                            }if(ini < fim){
                                return{
                                    valid: true
                                };
                            }if(ini === fim){
                                return{
                                    valid: false,
                                    message: "As datas não podem ser iguais"
                                };
                            }
                        }
                    }
                }
            },
//                validators: {
//                    callback: {
//                        message: "Por favor, a data de iníco deve ser maior ou igual a data de hoje",
//                        callback: function(value, validator){
//                                // o "+ 00:00:00:00" faz o JS ignorar o Timezone
//                                var date = new Date(value + " 00:00:00:00");
//                                var today = new Date();
//
//                                // Zera a hora das duas variÃ¡veis para comparar
//                                // apenas a data
//                                today.setHours(0,0,0,0);
//                                date.setHours(0,0,0,0);
//
//                                // forÃ§a a reescrita da data fim
//                                $("#idfim_per_estag").val('');
//
//                                return date >= today ;
//                        }
//                    }
//                }
//            },
            fim_per_estag: {
                validators:{
                    callback: {
                        callback: function(fim, validator, $field){
                            
                            var ini = $("#idini_per_estag").val();
                            
                            var fullDate = new Date();
                            
                            var twoDigitMonth = ((fullDate.getMonth().length+1) === 1)? (fullDate.getMonth()+1) : '0' + (fullDate.getMonth()+1);
                            
                            var day = fullDate.getDate();
                            
                            if(day <= 9){
                                day = "0" + day;
                            }

                            // var currentDate = fullDate.getFullYear()+ "-" + twoDigitMonth + "-" +day;
                            
                            // if(fim <= currentDate && fim !== ""){
                            //     return{
                            //         valid: false,
                            //         message: "Por favor, a data final deve ser maior que a data de hoje"
                            //     };
                            //}
                            if(ini === "" && fim === ""){
                                return{
                                    valid: true
                                };
                            }if(ini !== "" && fim === ""){
                                return{
                                    valid: false,
                                    message: "A data final não pode estar em branco"
                                };
                            }if(fim > ini && ini !== ""){
                                return{
                                    valid: true
                                };
                            }if(fim < ini){
                                return{
                                    valid: false,
                                    message: "A data final não pode ser menor que a data inicial"
                                };
                            }if(fim === ini){
                                return{
                                    valid: false,
                                    message: "As datas não podem ser iguais"
                                };
                            }if(fim > ini && ini === ""){
                                return{
                                    valid: false,
                                    message: 'A data inicial não pode estar em branco.'
                                };
                            }if(ini === ""){
                                return{
                                    valid: false
                                };
//                validators: {
//                        callback: {
//                                message: "Por favor, a data final deve ser maior do que a data inicial",
//                                callback: function(value, validator){
//                                    var fim = new Date(value + " 00:00:00:00");
//                                    var ini = new Date($("#idini_per_estag").val() + " 00:00:00:00");
//
//                                    fim.setHours(0,0,0,0);
//                                    ini.setHours(0,0,0,0);
//
//                                    return fim > ini;
//                                }
//                        }
//                }
//		
//            },    
                            }
                        },
                        noEmpty: {
                            message: "tesetset"
                    
                        }
                    }
                }
            },
            fim_nova_jornada : {
                validators:{
                    callback: {
                        callback: function(fim, validator, $field){
                            
                            var ini = $("#idini_nova_jornada").val();                             
                           
                            if(ini === "" && fim === ""){
                                return{
                                    valid: true
                                };
                            }if(ini !== "" && fim === ""){
                                return{
                                    valid: false,
                                    message: "A data final não pode estar em branco"
                                };
                            }if(fim > ini && ini !== ""){
                                return{
                                    valid: true
                                };
                            }if(fim < ini){
                                return{
                                    valid: false,
                                    message: "A data final não pode ser menor que a data inicial"
                                };
                            }if(fim === ini){
                                return{
                                    valid: false,
                                    message: "As datas não podem ser iguais"
                                };
                            }if(fim > ini && ini === ""){
                                return{
                                    valid: false,
                                    message: 'A data inicial não pode estar em branco.'
                                };
                            }if(ini === ""){
                                return{
                                    valid: false
                                };   
                            }
                        },
                        noEmpty: {
                            message: "tesetset"
                    
                        }
                    }
                }
            },
            // fim_per_estag_aditivo: {
            //     validators:{
            //         callback: {
            //             callback: function(fim, validator, $field){
                            
            //                 var fullDate = new Date();
                            
            //                 var twoDigitMonth = ((fullDate.getMonth().length+1) === 1)? (fullDate.getMonth()+1) : '0' + (fullDate.getMonth()+1);
                            
            //                 var day = fullDate.getDate();
                            
            //                 if(day <= 9){
            //                     day = "0" + day;
            //                 }
            //                 if(ini === "" && fim === ""){
            //                     return{
            //                         valid: true
            //                     };
            //                 }if(ini !== "" && fim === ""){
            //                     return{
            //                         valid: false,
            //                         message: "A data final não pode estar em branco"
            //                     };
            //                 }if(fim > ini && ini !== ""){
            //                     return{
            //                         valid: true
            //                     };
            //                 }if(fim < ini){
            //                     return{
            //                         valid: false,
            //                         message: "A data final não pode ser menor que a data inicial"
            //                     };
            //                 }if(fim === ini){
            //                     return{
            //                         valid: false,
            //                         message: "As datas não podem ser iguais"
            //                     };
            //                 }if(fim > ini && ini === ""){
            //                     return{
            //                         valid: false,
            //                         message: 'A data inicial não pode estar em branco.'
            //                     };
            //                 }if(ini === ""){
            //                     return{
            //                         valid: false
            //                     };   
            //                 }
            //             },
            //             noEmpty: {
            //                 message: "tesetset"
                    
            //             }
            //         }
            //     }
            // },
            fim_per_estag2:{
                validators:{
                    callback: {
                        callback: function(fim, validator, $field){
                            
                            var ini = $("#idini_per_estag2").val();
                            
                            
                            if(ini === "" && fim === ""){
                                return{
                                    valid: true
                                };
                            }if(ini !== "" && fim === ""){
                                return{
                                    valid: false,
                                    message: "A data final não pode estar em branco se a data inicial estiver preenchida"
                                };
                            }if(fim > ini && ini !== ""){
                                return{
                                    valid: true
                                };
                            }if(fim < ini){
                                return{
                                    valid: false,
                                    message: "A data final não pode ser menor que a data inicial"
                                };
                            }if(fim === ini){
                                return{
                                    valid: false,
                                    message: "As datas não podem ser iguais"
                                };
                            }if(fim > ini && ini === ""){
                                return{
                                    valid: false,
                                    message: 'A data inicial não pode estar em branco.'
                                };
                            }if(ini === ""){
                                return{
                                    valid: false
                                };
                            }
                        }
                    }
                }
            },
            ini_per_estag2:{
                validators:{
                    callback: {
                        callback: function(ini, validator){
                            
                            var fim = $("#idfim_per_estag2").val();
                            
                            
                            if(ini === "" && fim === ""){
                                return{
                                    valid: true
                                };
                            }if(fim !== "" && ini === ""){
                                return{
                                  valid: false,
                                    message: "A data inicial não pode estar em branco se a data final estiver preenchida"
                                };
                            }if(fim > ini && fim !== ""){
                                return{
                                    valid: true
                                };
                            }if(ini >= fim && fim === ""){
                                return{
                                    valid: false,
                                    message: "A data final não pode estar em branco."
                                };
                            }if(fim < ini){
                                return{
                                    valid: false,
                                    message: "a data inicial não pode ser maior que a data final"
                                };    
                            }if(ini < fim){
                                return{
                                    valid: true
                                };
                            }if(ini === fim){
                                return{
                                    valid: false,
                                    message: "As datas não podem ser iguais"
                                };
                            }
                        }
                    }
                }
            },
            // fim_per_estag_aditivo:{
            //     validators: {
            //         date: {
            //             format: "YYYY/MM/DD"
            //         }
            //     }
            // },
            data_chegada_pais:{
                validators: {
                    callback: {
                        callback: function(value, validator){
                            return{
                                valid: true
                            };
                        }
                    }
                }
            },
            data_emissao_titulo:{
                validators: {
                    callback: {
                        callback: function(value, validator){
                            return{
                                valid: true
                            };
                        }
                    }
                }
            },
            curso_aluno: {
                validators: {
                    notEmpty: {
                        message: 'Por favor insira o nome Completo do seu Curso'
                    },
                        stringLength: {
                        min: 3
                    }
                }
            },
            estag_hr_jor_dia2:{
                validators:{
                    callback:{
                        callback: function(value, validator){
                            console.log("pass here");
                            if(value === "4"){
                                $("#idjor_semanal").val(20 + " horas");
                                return{
                                    valid: true
                                };
                            }if(value === "6"){
                                $("#idjor_semanal").val(30 + " horas");
                                return{
                                    valid: true
                                };
                            }else{
                                $("#idjor_semanal").val("");
                                return{
                                    valid: false,
                                    message: "Favor escolher uma Jornada"
                                };
                            }
                        }
                    }
                }
            },
            jor_semanal2:{
                validators: {
                    callback: {
                        callback: function(value, validator){
                            if(value !== ""){
                                return{
                                    valid: true
                                };
                            }else{
                                return{
                                    valid: false,
                                    message: "Favor escolher uma Jornada Diária"
                                };
                            }    
                        }
                    }
                }
            },
            email_aluno: {
                validators: {
                    notEmpty: {
                        message: 'Por favor insira um endereço de Email'
                    },
                    emailAddress: {
                        message: 'Por favor insira um endereço de Email válido'
                    }
                }
            },
            email_repre_empr: {
                validators: {
                    notEmpty: {
                        message: 'Por favor insira um endereço de Email'
                    },
                    emailAddress: {
                        message: 'Por favor insira um endereço de Email válido'
                    }
                }
            },
            email_supervisor: {
                validators: {
                    notEmpty: {
                        message: 'Por favor insira um endereço de Email'
                    },
                    emailAddress: {
                        message: 'Por favor insira um endereço de Email válido'
                    }
                }
            },
            tel_orient: {
                validators: {
                    phone: {
                        country: 'BR',
                        message: 'Por favor forneça um telefone válido com DDD'
                    }
                }
            },
            cel_aluno: {
                validators: {
                    notEmpty: {
                        message: 'Por favor forneça um telefone de contato'
                    },
                    phone: {
                        country: 'BR',
                        message: 'Por favor forneça um telefone válido com DDD'
                    }
                }
            },
            tel_sup: {
                validators: {
                    phone: {
                        country: 'BR',
                        message: 'Por favor forneça um telefone válido com DDD'
                    }
                }
            },
            tel_aluno: {
                validators: {
                    notEmpty: {
                        message: 'Por favor forneça um telefone de contato'
                    },
                    phone: {
                        country: 'BR',
                        message: 'Por favor forneça um telefone válido com DDD'
                    }
                }
            },
            tel_supervisor: {
                validators: {
                    notEmpty: {
                        message: 'Por favor forneça um telefone de contato'
                    },
                    phone: {
                        country: 'BR',
                        message: 'Por favor forneça um telefone válido com DDD'
                    }
                }
            },
            tel_emp: {
                validators: {
                    notEmpty: {
                        message: 'Por favor forneça um telefone de contato'
                    },
                    phone: {
                        country: 'BR',
                        message: 'Por favor forneça um telefone válido com DDD'
                    }
                }
            },
            rua_emp: {
                validators: {
                     stringLength: {
                        min: 8
                    },
                    notEmpty: {
                        message: 'Por favor insira o nome da sua rua'
                    }
                }
            },
            rua_aluno: {
                validators: {
                     stringLength: {
                        min: 8
                    },
                    notEmpty: {
                        message: 'Por favor insira o nome da rua da empresa'
                    }
                }
            },
            cidade_aluno: {
                validators: {
                     stringLength: {
                        min: 3
                    },
                    notEmpty: {
                        message: 'Por favor forneça o nome da cidade'
                    }
                }
            },
            cidade_emp: {
                validators: {
                     stringLength: {
                        min: 3
                    },
                    notEmpty: {
                        message: 'Por favor forneça o nome da cidade'
                    }
                }
            },
            cpf_aluno: {
                validators: {
                    callback: {
                        message: 'CPF Inválido',
                        callback: function(value) {
                            //retira mascara e nao numeros       
                            cpf = value.replace(/[^\d]+/g,'');    
                            if(cpf == '') return false; 

                            if (cpf.length != 11) return false;

                            // testa se os 11 digitos são iguais, que não pode.
                            var valido = 0; 
                            for (i=1; i < 11; i++){
                             if (cpf.charAt(0)!=cpf.charAt(i)) valido =1;
                            }
                            if (valido==0) return false;

                            //  calculo primeira parte  
                            aux = 0;    
                            for (i=0; i < 9; i ++)       
                             aux += parseInt(cpf.charAt(i)) * (10 - i);  
                             check = 11 - (aux % 11);  
                             if (check == 10 || check == 11)     
                              check = 0;    
                             if (check != parseInt(cpf.charAt(9)))     
                              return false;      

                            //calculo segunda parte  
                            aux = 0;    
                            for (i = 0; i < 10; i ++)        
                             aux += parseInt(cpf.charAt(i)) * (11 - i);  
                            check = 11 - (aux % 11);  
                            if (check == 10 || check == 11) 
                             check = 0;    
                            if (check != parseInt(cpf.charAt(10)))
                             return false;       
                            return true; 
                        }
                    }
                }
            },
            cpf_supervisor: {
                validators: {
                    callback: {
                        message: 'CPF Inválido',
                        callback: function(value) {
                            //retira mascara e nao numeros       
                            cpf = value.replace(/[^\d]+/g,'');    
                            if(cpf == '') return false; 

                            if (cpf.length != 11) return false;

                            // testa se os 11 digitos são iguais, que não pode.
                            var valido = 0; 
                            for (i=1; i < 11; i++){
                             if (cpf.charAt(0)!=cpf.charAt(i)) valido =1;
                            }
                            if (valido==0) return false;

                            //  calculo primeira parte  
                            aux = 0;    
                            for (i=0; i < 9; i ++)       
                             aux += parseInt(cpf.charAt(i)) * (10 - i);  
                             check = 11 - (aux % 11);  
                             if (check == 10 || check == 11)     
                              check = 0;    
                             if (check != parseInt(cpf.charAt(9)))     
                              return false;      

                            //calculo segunda parte  
                            aux = 0;    
                            for (i = 0; i < 10; i ++)        
                             aux += parseInt(cpf.charAt(i)) * (11 - i);  
                            check = 11 - (aux % 11);  
                            if (check == 10 || check == 11) 
                             check = 0;    
                            if (check != parseInt(cpf.charAt(10)))
                             return false;       
                            return true; 
                        }
                    }
                }
            },
            cpf_contratante: {
                validators: {
                    callback: {
                        message: 'CPF Inválido',
                        callback: function(value) {
                            //retira mascara e nao numeros       
                            cpf = value.replace(/[^\d]+/g,'');    
                            if(cpf == '') return false; 

                            if (cpf.length != 11) return false;

                            // testa se os 11 digitos são iguais, que não pode.
                            var valido = 0; 
                            for (i=1; i < 11; i++){
                             if (cpf.charAt(0)!=cpf.charAt(i)) valido =1;
                            }
                            if (valido==0) return false;

                            //  calculo primeira parte  
                            aux = 0;    
                            for (i=0; i < 9; i ++)       
                             aux += parseInt(cpf.charAt(i)) * (10 - i);  
                             check = 11 - (aux % 11);  
                             if (check == 10 || check == 11)     
                              check = 0;    
                             if (check != parseInt(cpf.charAt(9)))     
                              return false;      

                            //calculo segunda parte  
                            aux = 0;    
                            for (i = 0; i < 10; i ++)        
                             aux += parseInt(cpf.charAt(i)) * (11 - i);  
                            check = 11 - (aux % 11);  
                            if (check == 10 || check == 11) 
                             check = 0;    
                            if (check != parseInt(cpf.charAt(10)))
                             return false;       
                            return true; 
                        }
                    }
                }
            },
            cpf_repre_empr: {
                validators: {
                    callback: {
                        message: 'CPF Inválido',
                        callback: function(value) {
                            //retira mascara e nao numeros       
                            cpf = value.replace(/[^\d]+/g,'');    
                            if(cpf == '') return false; 

                            if (cpf.length != 11) return false;

                            // testa se os 11 digitos são iguais, que não pode.
                            var valido = 0; 
                            for (i=1; i < 11; i++){
                             if (cpf.charAt(0)!=cpf.charAt(i)) valido =1;
                            }
                            if (valido==0) return false;

                            //  calculo primeira parte  
                            aux = 0;    
                            for (i=0; i < 9; i ++)       
                             aux += parseInt(cpf.charAt(i)) * (10 - i);  
                             check = 11 - (aux % 11);  
                             if (check == 10 || check == 11)     
                              check = 0;    
                             if (check != parseInt(cpf.charAt(9)))     
                              return false;      

                            //calculo segunda parte  
                            aux = 0;    
                            for (i = 0; i < 10; i ++)        
                             aux += parseInt(cpf.charAt(i)) * (11 - i);  
                            check = 11 - (aux % 11);  
                            if (check == 10 || check == 11) 
                             check = 0;    
                            if (check != parseInt(cpf.charAt(10)))
                             return false;       
                            return true; 
                        }
                    }
                }
            },
            cep_aluno: {
                validators: {
                    notEmpty: {
                        message: 'Por favor forneça um CEP'
                    },
                    zipCode: {
                        country: 'BR',
                        message: 'Por favor forneça um CEP válido'
                    }
                }
            },
            cnpj_emp:{
                validators: {
                    notEmpty: {
                        message: 'Por favor coloque o CNPJ'
                    },
                    vat:{
                        country: 'BR',
                        message: 'Por favor coloque um CNPJ válido'
                    }
                }    
            },
            atividades_programadas_consideracao:{
                validators:{
                    callback:{
                        callback: function(value, validator){
                             var resp_ativ = $("#idatividades_programadas").val();
                            if(resp_ativ === "Não" || resp_ativ === "Parcialmente"){
                                if(value === ""){
                                    return{
                                        valid: false,
                                        message: "Respondendo Não ou Parcialmente este campo torna-se obrigatório."
                                    };
                                }else{
                                    return{
                                        valid: true
                                    };
                                }
                            }else{
                                return{
                                    valid: true
                                };
                            }
                        }
                    }
                }
            },
            consideracao_atividades_programadas_coord_dqforma:{
                validators:{
                    callback:{
                        callback: function(value, validator){
                             var resp_ativ = $("#idcompanhamento_atividades_coord").val();
                            if(resp_ativ === "Frequentemente" || resp_ativ === "Raramente"){
                                if(value === ""){
                                    return{
                                        valid: false,
                                        message: "Respondendo Frequentemente ou Raramente este campo torna-se obrigatório."
                                    };
                                }else{
                                    return{
                                        valid: true
                                    };
                                }
                            }else{
                                return{
                                    valid: true
                                };
                            }
                        }
                    }
                }
            },
            consideracao_atividades_programadas_prof_dqforma:{
                validators:{
                    callback:{
                        callback: function(value, validator){
                             var resp_ativ = $("#idacompanhamento_atividades_prof").val();
                            if(resp_ativ === "Frequentemente" || resp_ativ === "Raramente"){
                                if(value === ""){
                                    return{
                                        valid: false,
                                        message: "Respondendo Frequentemente ou Raramente este campo torna-se obrigatório."
                                    };
                                }else{
                                    return{
                                        valid: true
                                    };
                                }
                            }else{
                                return{
                                    valid: true
                                };
                            }
                        }
                    }
                }
            }
        }
    });

    $('#idprimeiro_inicio_periodo_recesso').change(function(e){
        $('.form').bootstrapValidator('revalidateField','primeiro_fim_periodo_recesso');
    });
    $('#idprimeiro_fim_periodo_recesso').change(function(e){
        $('.form').bootstrapValidator('revalidateField','primeiro_inicio_periodo_recesso');
    });
    $('#idsegundo_inicio_periodo_recesso').change(function(e){
        $('.form').bootstrapValidator('revalidateField','segundo_fim_periodo_recesso');
    });
    $('#idsegundo_fim_periodo_recesso').change(function(e){
        $('.form').bootstrapValidator('revalidateField','segundo_inicio_periodo_recesso');
    });
    $('#idterceiro_inicio_periodo_recesso').change(function(e){
        $('.form').bootstrapValidator('revalidateField','terceiro_fim_periodo_recesso');
    });
    $('#idterceiro_fim_periodo_recesso').change(function(e){
        $('.form').bootstrapValidator('revalidateField','terceiro_inicio_periodo_recesso');
    });
    $('#idfim_per_estag').change(function(e){
        $('.form').bootstrapValidator('revalidateField','ini_per_estag');
    });
    $('#idini_per_estag').change(function(e){
        $('.form').bootstrapValidator('revalidateField','fim_per_estag');
    });
    $('#idfim_per_estag2').change(function(e){
        $('.form').bootstrapValidator('revalidateField','ini_per_estag2');
    });
    $('#idini_per_estag2').change(function(e){
        $('.form').bootstrapValidator('revalidateField','fim_per_estag2');
    });
    $('#idatividades_programadas').change(function(e){
        $('.form').bootstrapValidator('revalidateField','atividades_programadas_consideracao');
    });
    $('#idcompanhamento_atividades_coord').change(function(e){
        $('.form').bootstrapValidator('revalidateField','consideracao_atividades_programadas_coord_dqforma');
    });
    $('#idacompanhamento_atividades_prof').change(function(e){
        $('.form').bootstrapValidator('revalidateField','consideracao_atividades_programadas_prof_dqforma');
    });
    $('#idestag_hr_jor_dia2').change(function(e){
        $('.form').bootstrapValidator('revalidateField','jor_semanal2');
    });
    // $('#idfim_per_estag_aditivo').change(function(e){            
    //     $('.form').bootstrapValidator('revalidateField','fim_per_estag_aditivo');
    // });
    $('#idfim_nova_jornada').change(function(e){            
        $('.form').bootstrapValidator('revalidateField','fim_nova_jornada');
    });
    
    $('#idperiodo_estagio_antigo').change(function(e){
        $('.form').bootstrapValidator('revalidateField','periodo_estagio_novo');
    });
    $('#idperiodo_estagio_novo').change(function(e){
        $('.form').bootstrapValidator('revalidateField','periodo_estagio_antigo');
    });
    $('#idmodalidade_estagio_nova').change(function(e){
        $('.form').bootstrapValidator('revalidateField','modalidade_estagio_antiga');
    });
    $('#idmodalidade_estagio_antiga').change(function(e){
        $('.form').bootstrapValidator('revalidateField','modalidade_estagio_nova');
    });
    $('#idgrau_parentesco1').change(function(e){
        $('.form').bootstrapValidator('revalidateField','nome_parente1');
    });
    $('#idnome_parente1').change(function(e){
        $('.form').bootstrapValidator('revalidateField','grau_parentesco1');
    });
    $('#idgrau_parentesco2').change(function(e){
        $('.form').bootstrapValidator('revalidateField','nome_parente2');
    });
    $('#idnome_parente2').change(function(e){
        $('.form').bootstrapValidator('revalidateField','grau_parentesco2');
    });
    $('#idgrau_parentesco3').change(function(e){
        $('.form').bootstrapValidator('revalidateField','nome_parente3');
    });
    $('#idnome_parente3').change(function(e){
        $('.form').bootstrapValidator('revalidateField','grau_parentesco3');
    });
    $('#idgrau_parentesco4').change(function(e){
        $('.form').bootstrapValidator('revalidateField','nome_parente4');
    });
    $('#idnome_parente4').change(function(e){
        $('.form').bootstrapValidator('revalidateField','grau_parentesco4');
    });
    $('#idgrau_parentesco5').change(function(e){
        $('.form').bootstrapValidator('revalidateField','nome_parente5');
    });
    $('#idnome_parente5').change(function(e){
        $('.form').bootstrapValidator('revalidateField','grau_parentesco5');
    });
});


$( function() {
    $(document).tooltip({
        show: {
            effect: "slideDown",
            delay: 250
        }
    });
});
