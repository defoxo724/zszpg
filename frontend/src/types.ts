export type UserType = 'ADMIN' | 'ORDERER'

export interface User {
	id: number
	login: string
	type: UserType
}

export interface MealCategory {
	id: number
	name: string
}

export interface Meal {
	id: number
	name: string
	description: string
	price: number
	mealCategoryId: number
	imageUrl: string | null
}

export interface MealInput {
	name: string
	description: string
	price: number
	mealCategoryId: number
	image: File | null
}

export interface CartItem {
	id: number
	mealId: number
	quantity: number
}

export interface Cart {
	id: number
	comment: string
	cartItems: CartItem[]
}
