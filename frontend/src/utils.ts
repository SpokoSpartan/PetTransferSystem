export const utils = {
	created() {
		console.log('mixing')
	},
	methods: {
		getAgeWithBirthDate: function (value: string) {
			let date = new Date(value);
			return date.getDay() + "." + date.getMonth() + "." + date.getFullYear();
		},
		translateBooleanToHuman: function (value: boolean) {
			if (value) {
				return "yes"
			}
			return "no"
		}
	}
}
