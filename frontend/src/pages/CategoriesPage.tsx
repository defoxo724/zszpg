import { useEffect, useState } from 'react'
import { CategoryApi } from '../api/CategoryApi'
import MealCategoryForm from '../components/MealCategoryForm'
import StatusMessage from '../components/StatusMessage'
import { useAuth } from '../context/AuthContext'
import type { MealCategory } from '../types'
import { getErrorMessage } from '../utils/getErrorMessage'

const CategoriesPage = () => {
	const { user } = useAuth()
	const isAdmin = user?.type === 'ADMIN'
	const [categories, setCategories] = useState<MealCategory[]>([])
	const [categoryBeingEdited, setCategoryBeingEdited] = useState<MealCategory | null>(null)
	const [error, setError] = useState<string | null>(null)

	const loadCategories = async () => {
		try {
			setCategories(await CategoryApi.getCategories())
			setError(null)
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się pobrać kategorii.'))
		}
	}

	useEffect(() => {
		void loadCategories()
	}, [])

	const handleSaveCategory = async (name: string) => {
		try {
			if (categoryBeingEdited) {
				await CategoryApi.updateCategory(categoryBeingEdited.id, name)
			} else {
				await CategoryApi.createCategory(name)
			}

			setCategoryBeingEdited(null)
			await loadCategories()
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się zapisać kategorii.'))
		}
	}

	const handleDeleteCategory = async (categoryId: number) => {
		const deletionConfirmed = confirm('Usunąć kategorię?')
		if (!deletionConfirmed) {
			return
		}

		try {
			await CategoryApi.deleteCategory(categoryId)
			await loadCategories()
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się usunąć kategorii.'))
		}
	}

	return (
		<>
			<h1 className="mb-4">Kategorie posiłków</h1>
			<StatusMessage message={error} />
			{isAdmin && (
				<MealCategoryForm
					categoryBeingEdited={categoryBeingEdited}
					onSave={handleSaveCategory}
					onCancel={() => setCategoryBeingEdited(null)}
				/>
			)}
			<div className="list-group">
				{categories.map((category) => (
					<div
						className="list-group-item d-flex justify-content-between align-items-center"
						key={category.id}
					>
						<span>{category.name}</span>
						{isAdmin && (
							<span>
								<button
									className="btn btn-sm btn-outline-primary me-2"
									onClick={() => setCategoryBeingEdited(category)}
								>
									Edytuj
								</button>
								<button
									className="btn btn-sm btn-outline-danger"
									onClick={() => handleDeleteCategory(category.id)}
								>
									Usuń
								</button>
							</span>
						)}
					</div>
				))}
			</div>
			{!categories.length && <p>Brak kategorii.</p>}
		</>
	)
}
export default CategoriesPage
