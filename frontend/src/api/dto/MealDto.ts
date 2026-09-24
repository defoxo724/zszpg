import type { Meal } from '../../types'

export type MealCreateRequest = Omit<Meal, 'id' | 'imageUrl'>
export type MealUpdateRequest = MealCreateRequest
export type MealCreateResponse = Meal
export type MealUpdateResponse = Meal
export type MealGetAllResponseListElement = Meal

export interface MealGetAllResponse {
	meals: Meal[]
}
