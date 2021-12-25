const Registration = { template: '<registration></registration>' }
const Login = { template: '<login></login>' }
const UserProfile = { template: '<userProfile></userProfile>' }
const UsersOverview = {template: '<usersOverview></usersOverview>' }
const NewRestaurant = {template: '<newRestaurant></newRestaurant>' }
const EmployeeRegistration = {template: '<employeeRegistration></employeeRegistration>' }
const ManagerRegistration = {template: '<managerRegistration></managerRegistration>'}

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
		{ path: '/registration', component: Registration },
		{ path: '/login', component: Login },
	    { path: '/userProfile', component: UserProfile },
		{ path: '/usersOverview', component: UsersOverview },
		{ path: '/newRestaurant', component: NewRestaurant },
		{ path: '/employeeRegistration', component: EmployeeRegistration },
		{ path: '/managerRegistration', component: ManagerRegistration }
	]
});

var app = new Vue({
	router,
	el: '#ordering_system'
});