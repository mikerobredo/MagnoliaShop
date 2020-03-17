(function ($) {

	"use strict";

	$.fn.paymentInfo = function (method) {

		// Global variables.
		var methods,
			helpers,
			events,
			ccDefinitions,
			opts,
			pluginName = "paymentInfo";

		// Events map for the matchNumbers() function.
		events = $.map(['change', 'blur', 'keyup', 'keypress', 'keydown'], function (v) {
			return v + '.' + pluginName;
		}).join(' ');

		// Credit card regex patterns.
		ccDefinitions = {
			'visa': /^4/,
			'mc': /^5[1-5]/,
			'amex': /^3(4|7)/,
			'disc': /^6011/,
			'dinners' : /^30[0-5]/,
			'jcb' : /^35(2[89]|[3-8][0-9])/
		};

		helpers = {

			// Determine if the number being give corresponds to a credit card
			getCreditCardType: function (number) {

				var ccType;

				$.each(ccDefinitions, function (i, v) {

					if (v.test(number)) {
						ccType = i;
						return false;
					}

				});

				return ccType;

			},

			matchNumbers: function (el) {

				var cardNumber = el.data("ccNumber") || el.val(),
					ccType = helpers.getCreditCardType(cardNumber);

				if (el.val() !== "") {
					if (ccType !== "") {
						$("." + opts.cardImageClass)
							.addClass(ccType);
					} else {
						$("." + opts.cardImageClass)
							.removeClass()
							.addClass(opts.cardImageClass);
					}
				} else {
					$("." + opts.cardImageClass)
						.removeClass()
						.addClass(opts.cardImageClass)
						.data("ccNumber", "");
				}

				// Check the card type to see if it's an Amex or Dinners

				if (ccType === "amex") {
					el.inputmask({ mask: "9999 999999 99999", oncomplete: helpers.creditCardComplete });
					$("." + opts.cardCvvClass).inputmask({ mask: "9999", oncomplete: helpers.cvvComplete });
				} 

				else if (ccType === "dinners") {
					el.inputmask({ mask: "9999 9999 9999 99", oncomplete: helpers.creditCardComplete });
				}

				else {
					el.inputmask({ mask: "9999 9999 9999 9999", oncomplete: helpers.creditCardComplete });
					$("." + opts.cardCvvClass).inputmask({ mask: "999", oncomplete: helpers.cvvComplete });
				}
			},

			creditCardComplete: function () {

				// We need to get the credit card field and the unmasked value of the field.
				var element = $("." + opts.cardNumberClass),
					uvalue = element.inputmask("unmaskedvalue"),
					ccType = helpers.getCreditCardType(uvalue);

				// Make sure the card is valid
				if (ccType === undefined || helpers.ccLuhnCheck(uvalue) === false) {
					$(element)
						.addClass("invalid shake");
					
					$('.' + opts.cardInstructionClass).addClass("invalid");

					helpers.updateInstruction(opts.messageCardNumberError);

					return;
				}

				else {
					$(element)
						.removeClass("invalid shake")
						.addClass("valid");

					$('.' + opts.cardInstructionClass).removeClass("invalid");
				}

				$(element)
					.unbind("blur focus click keydown keypress")
					.bind("focus click keydown", function (e) {
						if (e.type === "focus" || e.type === "click" || (e.shiftKey && e.keyCode === 9)) {
							helpers.beginCreditCard($(element));
							helpers.updateInstruction(opts.messageEnterCardNumber);
						}
					});

				if (window.navigator.standalone || !Modernizr.touch) {
					// Focus on the credit card expiration input.
					$("." + opts.cardExpirationClass).focus().val($.trim($("." + opts.cardExpirationClass).val()));
					helpers.updateInstruction(opts.messageExpiration);
				}

			},

			expirationComplete: function () {

				$("." + opts.cardImageClass).addClass("cvv2");

				$("." + opts.cardExpirationClass)
					.addClass("full")
					.bind("keydown", function (e) {
						if (e.keyCode === 8 && $(this).val() === "") {
							$(this).removeClass("full");
							if (window.navigator.standalone || !Modernizr.touch) {
								helpers.updateInstruction(opts.messageEnterCardNumber);
							}
						}
					});


				var date = new Date();
				var MM = date.getMonth() +1 ;
				var YY = date.getFullYear().toString().substr(-2);

				var YYinput = $(".card-expiration").val().substr(-2);
				var MMinput = $(".card-expiration").val().substr(0,2);
				var cardNumber = $('.card-number');
				var cvvNumber = $('.card-cvv');

				if(YYinput < YY){
					$(".card-expiration").addClass("invalid shake");
					helpers.updateInstruction(opts.messageExpirationError);
				}else if(YYinput == YY && MMinput <= MM) {
					$(".card-expiration").addClass("invalid shake");
					helpers.updateInstruction(opts.messageExpirationError);
				}else{
					$(".card-expiration").removeClass("invalid shake");
					$(".card-expiration").addClass("valid");

					if (window.navigator.standalone || !Modernizr.touch) {
						setTimeout(function () {
							$("." + opts.cardCvvClass).focus();
							helpers.updateInstruction(opts.messageCVV);
						}, 220);
					}

					helpers.updateInstruction(opts.messageCVV);
					}


				if(cardNumber.hasClass("valid") && cvvNumber.hasClass("full")){
						$("." + opts.fieldsetClass)
						.addClass('valid');
						$("." + opts.cardInstructionClass)
						.addClass('valid');
						helpers.updateInstruction(opts.messageSuccess);
					}


			},

			cvvComplete: function () {

				$("." + opts.cardImageClass).removeClass("cvv2");

				$("." + opts.cardCvvClass)
					.addClass("full")
					.unbind("keydown blur")
					.bind("keydown", function (e) {
						if (e.keyCode === 8 || e.keyCode === 9) {
							if ($(this).val() === "") {
								$(this).removeClass("full");
								if (window.navigator.standalone || !Modernizr.touch) {
									$("." + opts.cardExpirationClass).focus();
									helpers.updateInstruction(opts.messageExpiration);
								}
							}
							$("." + opts.cardImageClass).removeClass("cvv2");
						}
					});

				var cardNumber = $('.card-number');
				var expirationNumber = $('.card-expiration');
				
				if(expirationNumber.hasClass("valid") && cardNumber.hasClass("valid")){
					$("." + opts.fieldsetClass)
					.addClass('valid');

					$("." + opts.cardInstructionClass)
					.addClass('valid');
					helpers.updateInstruction(opts.messageSuccess);
				}

			},


			
			beginCreditCard: function (element) {
				$(element)
					.unbind("keypress blur")
					.bind("keypress blur", function (e) {
						// Is it the enter key?
						if (e.keyCode === 13 || e.type === "blur") {
							var uvalue = $(element).inputmask("unmaskedvalue"),
								ccType = helpers.getCreditCardType(uvalue);

							// Make sure the number length is valid
							if ((ccType === "amex" && uvalue.length === 15) || (ccType !== "amex" && uvalue.length === 16)) {
								helpers.creditCardComplete();
							}

						}

					})
					.unbind("focus click keydown");
			},

			updateInstruction: function (message) {
				$('.card-instruction').html(message);
			},

			/**
			 * Luhn algorithm
			 * @author ShirtlessKirk. Copyright (c) 2012.
			 * @license WTFPL (http://www.wtfpl.net/txt/copying)
			*/
			ccLuhnCheck: (function (arr) {
				return function (ccNum) {
					var
						len = ccNum.length,
						bit = 1,
						sum = 0,
						val;
						
					while (len) {
						val = parseInt(ccNum.charAt(--len), 10);
						sum += (bit ^= 1) ? arr[val] : val;
					}
					
					return sum && sum % 10 === 0;
				};
			}([0, 2, 4, 6, 8, 1, 3, 5, 7, 9]))

		};

		$(".card-number").focus(function(){
			if($(this).hasClass("invalid")){
				helpers.updateInstruction(opts.messageCardNumberError);
			}else{
				helpers.updateInstruction(opts.messageEnterCardNumber);
			}
		});

		$(".card-expiration").focus(function(){
			if($(this).hasClass("invalid")){
				helpers.updateInstruction(opts.messageExpirationError);
			}else{
				helpers.updateInstruction(opts.messageExpiration);
			}
		});

		$(".card-cvv").focus(function(){
			helpers.updateInstruction(opts.messageCVV);
		});

		$(".card-number").keydown(function(){
		    if( $(this).val().length === 0 ) {
		       $(this).removeClass("invalid shake");
		    }
		});

		methods = {

			init: function (options) {

				// Get a copy of our configuration options
				opts = $.extend({}, $.fn.paymentInfo.defaults, options);

				// Configure the jQuery.inputmask plugin
				$.extend($.inputmask.defaults, {
					placeholder: " ",
					showMaskOnHover: false,
					overrideFocus: false
				});

				// Loop through our fieldset, find our inputs and initialize them.
				return this.each(function () {

					$(this)
						.find("." + opts.cardNumberClass)
							.inputmask({ mask: "9999 9999 9999 9999" })
							.before("<span class='" + opts.cardImageClass + "'></span>")
						.end()
						.find("." + opts.cardExpirationClass)
							.inputmask({
								mask: "m/q",
								clearIncomplete: true,
								oncomplete: helpers.expirationComplete
							})
						.end()
						.find("." + opts.cardCvvClass)
							.inputmask({ mask: "999" })
							//.addClass("hide")
							.focus(function () {
								$("." + opts.cardImageClass).addClass("cvv2");
							})
							.blur(function () {
								$("." + opts.cardImageClass).removeClass("cvv2");
							})
						.end()

						if(opts.cardInstruction) {
							$(this).
								after("<span class='" + opts.cardInstructionClass + "'>"+ opts.messageEnterCardNumber + "</span>");
						}

						helpers.matchNumbers($(this).find("." + opts.cardNumberClass).eq(0));

				}).unbind('.' + pluginName).bind(events, function () {
					helpers.matchNumbers($(this).find("." + opts.cardNumberClass).eq(0));
				});
			},

			destroy: function () {
				return this.unbind('.' + pluginName);
			}

		};

		// Plugin methods API
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		}
		if (typeof method === "object" || !method) {
			return methods.init.apply(this, arguments);
		}
		return $.error("Method does not exist in plugin");

	};


	// Plugin config options.
	$.fn.paymentInfo.defaults = {
		fieldsetClass: "credit-card-group",
		cardImageClass: "card-image",
		cardCvvClass: "card-cvv",
		cardExpirationClass: "card-expiration",
		cardNumberClass: "card-number",
		cardInstruction : true,
		cardInstructionClass: "card-instruction",
		animationWait: 0,
		focusDelay: 0,
		messageEnterCardNumber : "Introduce tu n&#250;mero de tarjeta",
		messageCardNumberError : "Introduce un n&#250;mero de tarjeta v&#225;lido",
		messageExpiration : "Introduce la fecha de caducidad de tu tarjeta",
		messageExpirationError : "Introduce una fecha de caducidad v&#225;lida",
		messageCVV : "Introduce los tres d&#237;gitos de seguridad que encontrar&#225;s en el reverso de tu tarjeta",
		messageSuccess : "Datos de tarjeta introducidos correctamente"
	};

}(jQuery));

$(".credit-card-group").paymentInfo();