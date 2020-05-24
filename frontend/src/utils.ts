export const utils = {
	created() {
		console.log("utils are available")
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
