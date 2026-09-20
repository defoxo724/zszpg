import type { Cart } from '../types'
import { axiosConfig } from './axiosConfig'

export class CartApi {
	static async getCart(id: number) {
		const response = await axiosConfig.get<Cart>(`/carts/${id}`)
		return response.data
	}

	static async getCurrentUserCart() {
		const response = await axiosConfig.get<Cart>('/carts/mine')
		return response.data
	}

	static async getCarts() {
		const response = await axiosConfig.get<{ carts: Cart[] }>('/carts')
		return response.data.carts
	}

	static async updateCart(id: number, comment: string) {
		const response = await axiosConfig.put<Cart>(`/carts/${id}`, { comment })
		return response.data
	}

	static async addMealToCart(cartId: number, mealId: number) {
		const response = await axiosConfig.post<Cart>(`/carts/${cartId}/meals/${mealId}`)
		return response.data
	}

	static async removeMealFromCart(cartId: number, mealId: number) {
		const response = await axiosConfig.delete<Cart>(`/carts/${cartId}/meals/${mealId}`)
		return response.data
	}
}
