const Registration = { template: '<registration></registration>' }
const Login = { template: '<login></login>' }
const UserProfile = { template: '<userProfile></userProfile>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
		{ path: '/registration', component: Registration },
		{ path: '/login', component: Login },
	    { path: '/userProfile', component: UserProfile }

	]
});

var app = new Vue({
	router,
	el: '#ordering_system'
});