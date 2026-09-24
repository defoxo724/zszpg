import { backendClient } from '../config/backendClient'
import type {
	AddMealToCartResponse,
	CartGetByIdResponse,
	RemoveMealFromCartResponse,
} from './dto/CartDto'

export const CartApi = {
	async getCart(id: number): Promise<CartGetByIdResponse> {
		const response = await backendClient.get<CartGetByIdResponse>(`/carts/${id}`)
		return response.data
	},

	async getCurrentUserCart(): Promise<CartGetByIdResponse> {
		const response = await backendClient.get<CartGetByIdResponse>('/carts/mine')
		return response.data
	},

	async addMealToCart(cartId: number, mealId: number): Promise<AddMealToCartResponse> {
		const response = await backendClient.post<AddMealToCartResponse>(
			`/carts/${cartId}/meals/${mealId}`,
		)
		return response.data
	},

	async removeMealFromCart(cartId: number, mealId: number): Promise<RemoveMealFromCartResponse> {
		const response = await backendClient.delete<RemoveMealFromCartResponse>(
			`/carts/${cartId}/meals/${mealId}`,
		)
		return response.data
	},
}
