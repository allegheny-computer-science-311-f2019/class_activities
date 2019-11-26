import gym
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
import random

env = gym.make("Taxi-v3")  # .env

print("Random state:")
env.reset()
env.render()

print("Action Space {}".format(env.action_space))
print("State Space {}".format(env.observation_space))

env.env.s = 101
print("State 101:")
env.render()

print(env.step(1))
env.render()


# https://www.kaggle.com/angps95/intro-to-reinforcement-learning-with-openai-gym
def Q_learning_train(env, alpha, gamma, epsilon, episodes):
    """Q Learning Algorithm with epsilon greedy

    Args:
        env: Environment
        alpha: Learning Rate --> Extent to which our Q-values are being updated
            in every iteration.
        gamma: Discount Rate --> How much importance we want to give to
            future rewards
        epsilon: Probability of selecting random action instead of the
            'optimal' action
        episodes: No. of episodes to train on

    Returns:
        Q-learning Trained policy

    """
    """Training the agent"""

    # Initialize Q table of 500 x 6 size (500 states and 6 actions) with all 0s
    q_table = np.zeros([env.observation_space.n, env.action_space.n])

    for i in range(1, episodes+1):
        state = env.reset()

        epochs, penalties, reward, = 0, 0, 0
        done = False

        while not done:
            if random.uniform(0, 1) < epsilon:
                # Explore action space randomly
                action = env.action_space.sample()
            else:
                # Exploit learned values by choosing optimal values
                action = np.argmax(q_table[state])

            next_state, reward, done, info = env.step(action)

            old_value = q_table[state, action]
            next_max = np.max(q_table[next_state])

            new_value = (1-alpha) * old_value + alpha * (reward+gamma*next_max)
            q_table[state, action] = new_value

            if reward == -10:
                penalties += 1

            state = next_state
            epochs += 1

        if i % 100 == 0:
            print(f"Episode: {i}")

    # Start with a random policy
    policy = np.ones([env.env.nS, env.env.nA]) / env.env.nA

    for state in range(env.env.nS):  # for each states
        best_act = np.argmax(q_table[state])  # find best action

        policy[state] = np.eye(env.env.nA)[best_act]  # update
        if(state == 101):
            print("State 101 q-table: ", q_table[101])
            print("State 101 selected action: ", best_act)
            print("State 101 policy: ", policy[state])
    print("Training finished.\n")
    return policy, q_table
# end_of Q_learning_train


def count(policy):
    curr_state = env.reset()
    counter = 0
    reward = None
    while reward != 20:
        state, reward, done, info = env.step(np.argmax(policy[curr_state]))
        curr_state = state
        counter += 1
    return counter


def view_policy(policy):
    curr_state = env.reset()
    counter = 0
    reward = None
    while reward != 20:
        state, reward, done, info = env.step(np.argmax(policy[0][curr_state]))
        curr_state = state
        counter += 1
        env.env.s = curr_state
        env.render()


env.reset()
env.render()

Q_learn_pol = Q_learning_train(env, 0.2, 0.95, 0.1, 10000)

print("Optimal Policy in Action")
view_policy(Q_learn_pol)

Q_counts = [count(Q_learn_pol[0]) for i in range(1000)]
print("An agent using a policy which has been improved using Q-learning takes"
      + " about an average of " + str(int(np.mean(Q_counts)))
      + " steps to successfully complete its mission.")
sns.distplot(Q_counts)
plt.title("Distribution of number of steps needed")
plt.show()
