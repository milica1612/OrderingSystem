const Registration = { template: '<registration></registration>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
		{ path: '/registration', component: Registration },
	]
});

var app = new Vue({
	router,
	el: '#ordering_system'
});