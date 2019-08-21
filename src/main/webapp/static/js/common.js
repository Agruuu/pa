/* 3E支部 - 登录系统 */

		/* 顶部下拉菜单 */ 
		$("#topNavTitle").click(function(){
			if( $(this).hasClass('open') ){
				$(this).removeClass('open');
				$("#navDownList").slideUp();
			}else{
				$(".topNavTitle").removeClass("open");
				$(".navDownList").hide();
				$(this).addClass('open');
				$("#navDownList").slideDown();
			}					 
		});
		$("#topNavTitle1").click(function(){
			if( $(this).hasClass('open') ){
				$(this).removeClass('open');
				$("#navDownList1").slideUp();
			}else{
				$(".topNavTitle").removeClass("open");
				$(".navDownList").hide();
				$(this).addClass('open');
				$("#navDownList1").slideDown();
			}					 
		});
		
		/* 点击登录选项 显示对应的登录框*/
		function showLoginBox(clickElem, loginBoxElem){
			$(clickElem).click(function(){
				$("#login_left").addClass("move");
				$(".loginBox").hide();
				$(loginBoxElem).show();
				
				//收起顶部下拉菜单 
				$(".navDownList").hide(); 
				$(".topNavTitle").removeClass("open");
			});	
		}
		
		showLoginBox("#toELogin", "#loginBox-3E");	//登录 支部中心
		showLoginBox("#toHome", "#loginBox-home");	//登录 党员主页
		
		showLoginBox("#toZeren", "#loginBox-zeren");	//登录 两个责任
		showLoginBox("#toJiandu", "#loginBox-jiandu");	//登录 日常监督
		showLoginBox("#toJubao", "#loginBox-jubao");	//登录 监督举报
		showLoginBox("#toJieshou", "#loginBox-jieshou");	//登录 接受举报
		showLoginBox("#toQingfeng", "#loginBox-qingfeng");	//登录 清风园地
		showLoginBox("#toGongche", "#loginBox-gongche");	//公车监管
		
		showLoginBox("#toLianzhenjy", "#loginBox-lianzhen");	//廉政教育

		//干部教育登录 切换
		$(".loginTab span").click(function(){
			var index = $(this).index();
			$(this).addClass("current").siblings().removeClass("current");
			$(this).parent().siblings(".loginForm").eq(index).show().siblings(".loginForm").hide();
		});
		
		

/* 通用TAB菜单 */

		$(".tabMenu li").click(function(){
			var index = $(this).index();
			$(this).addClass("current").siblings().removeClass("current");
			
			var tabContent = $(this).parent(".tabMenu").siblings(".tabContent");
			tabContent.eq(index).addClass("current").siblings().removeClass("current");
		});

/* 复选框自定义样式 */

		$(".checkbox").click(function(){
									  console.log(0);
			if( $(this).hasClass("selected") ){
				$(this).removeClass("selected");
			}else{
				$(this).addClass("selected");
			}
		});
		
/* 页面左侧折叠菜单 */
	
		$(".leftBar .module .more").click(function(){
			$(this).parents(".boxTitle").siblings("table").slideToggle();
		});
		
		/* E支部 目录栏折叠菜单 */
		$("#catalog").click(function(){
			var _this = $(this);
			if( $(this).hasClass("current") ){
				$(this).find(".list").slideUp(function(){
					 _this.attr("class", "module");
				});
			}else{
				$(this).find(".list").slideDown(function(){
					 _this.attr("class", "module open current");
				});
			}
		});
		
		
		

/* 党员教育 图片轮播 */
		if( $("#imgBox").length>0 ){
			$("#imgBox").owlCarousel({

				navigation : true, // Show next and prev buttons
				slideSpeed : 300,
				paginationSpeed : 400,
				singleItem:true

				// "singleItem:true" is a shortcut for:
				// items : 1, 
				// itemsDesktop : false,
				// itemsDesktopSmall : false,
				// itemsTablet: false,
				// itemsMobile : false

			});
		}
		
/* 3E支部登录前 图片轮播 */

		//旗委办党支部
		if( $("#imgTextFocus").length>0 ){
			$("#imgTextFocus").owlCarousel({

				navigation : true, // Show next and prev buttons
				slideSpeed : 300,
				paginationSpeed : 400,
				singleItem:true

				// "singleItem:true" is a shortcut for:
				// items : 1, 
				// itemsDesktop : false,
				// itemsDesktopSmall : false,
				// itemsTablet: false,
				// itemsMobile : false

			});
		}
		
		//集体经济
		if( $("#imgTextFocus2").length>0 ){
			$("#imgTextFocus2").owlCarousel({

				navigation : true, // Show next and prev buttons
				slideSpeed : 300,
				paginationSpeed : 400,
				singleItem:true

				// "singleItem:true" is a shortcut for:
				// items : 1, 
				// itemsDesktop : false,
				// itemsDesktopSmall : false,
				// itemsTablet: false,
				// itemsMobile : false

			});
		}
		
		//留影墙
		if( $("#picsList").length>0 ){
			$("#picsList").owlCarousel({

				navigation : true, // Show next and prev buttons
				slideSpeed : 300,
				paginationSpeed : 400,
				singleItem:false,
				items: 4

				// "singleItem:true" is a shortcut for:
				// items : 1, 
				// itemsDesktop : false,
				// itemsDesktopSmall : false,
				// itemsTablet: false,
				// itemsMobile : false

			});
		}
		
/* 后台管理 左侧菜单 点击收缩 */

		$(".leftBar .menuList > li .name").click(function(){
			var parent = $(this).parent();
			if( parent.hasClass("current") ){
				$(this).next("ul").slideUp(function(){
					parent.removeClass("current");
				});
			}else{
				$(this).next("ul").slideDown(function(){
					parent.addClass("current");
				});
				
				parent.siblings().removeClass("current");
				parent.siblings().find("ul").slideUp();
			}
		});
		

/* 后台管理系统 右侧 关闭打开的页面 */

		$(".rightBar .main .toolsBar .item i").click(function(){
			$(this).parent().remove();
		});
		
		
		
		
		
		
		
		