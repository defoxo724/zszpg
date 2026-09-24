export const validation = {
	meal: {
		name: { minLength: 3, maxLength: 100 },
		description: { minLength: 3, maxLength: 255 },
	},
	mealCategory: {
		name: { minLength: 3, maxLength: 100 },
	},
} as const
