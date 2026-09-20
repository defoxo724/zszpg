import { useEffect, useState } from 'react'
import { CategoryApi } from '../api/CategoryApi'
import { MealApi } from '../api/MealApi'
import MealEditorForm from '../components/MealEditorForm'
import MealsTable from '../components/MealsTable'
import StatusMessage from '../components/StatusMessage'
import { useAuth } from '../context/AuthContext'
import type { Meal, MealCategory, MealInput } from '../types'
import { getErrorMessage } from '../utils/getErrorMessage'

const MealsPage = () => {
	const { user } = useAuth()
	const isAdmin = user?.type === 'ADMIN'
	const [meals, setMeals] = useState<Meal[]>([])
	const [categories, setCategories] = useState<MealCategory[]>([])
	const [mealBeingEdited, setMealBeingEdited] = useState<Meal | null>(null)
	const [error, setError] = useState<string | null>(null)

	const loadMealsAndCategories = async () => {
		try {
			const [loadedMeals, loadedCategories] = await Promise.all([
				MealApi.getMeals(),
				CategoryApi.getCategories(),
			])

			setMeals(loadedMeals)
			setCategories(loadedCategories)
			setError(null)
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się pobrać danych.'))
		}
	}

	useEffect(() => {
		void loadMealsAndCategories()
	}, [])

	const handleSaveMeal = async (mealInput: MealInput) => {
		try {
			if (mealBeingEdited) {
				await MealApi.updateMeal(mealBeingEdited.id, mealInput)
			} else {
				await MealApi.createMeal(mealInput)
			}

			setMealBeingEdited(null)
			await loadMealsAndCategories()
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się zapisać posiłku.'))
		}
	}

	const handleDeleteMeal = async (mealId: number) => {
		const deletionConfirmed = confirm('Usunąć posiłek?')
		if (!deletionConfirmed) {
			return
		}

		try {
			await MealApi.deleteMeal(mealId)
			await loadMealsAndCategories()
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się usunąć posiłku.'))
		}
	}

	return (
		<>
			<h1 className="mb-4">Posiłki</h1>
			<StatusMessage message={error} />
			{isAdmin && (
				<MealEditorForm
					categories={categories}
					mealBeingEdited={mealBeingEdited}
					onSave={handleSaveMeal}
					onCancel={() => setMealBeingEdited(null)}
				/>
			)}
			<MealsTable
				meals={meals}
				categories={categories}
				isAdmin={isAdmin}
				onEdit={setMealBeingEdited}
				onDelete={handleDeleteMeal}
			/>
		</>
	)
}
export default MealsPage
